package com.sphinx.talk.ui.room

import android.R.attr
import android.app.Application
import android.app.Service
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.sphinx.talk.TalkApplication
import com.sphinx.talk.api.Message
import com.sphinx.talk.api.Room
import com.sphinx.talk.api.User
import io.socket.client.Socket
import kotlinx.coroutines.launch
import org.json.JSONException

import org.json.JSONObject

import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import okhttp3.WebSocket
import org.json.JSONArray
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.TypeVariable
import android.R.attr.port
import android.media.*

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import java.io.FileOutputStream
import java.util.concurrent.Executor


fun JSONObject.toUser(): User {
    return User(
        id = this.getInt("id"),
        firstName = this.getString("firstName"),
        lastName = this.getString("lastName"),
        avatar =  this.getString("avatar").takeIf { it !== "null" }
    )
}



fun JSONObject.toMessage(): Message {
    return Message(
        text = this.getString("text"),
        user = this.getJSONObject("user").toUser()
    )
}

fun <T> JSONArray.map(c:(JSONObject) -> T): MutableList<T> {
    val mutableList = mutableListOf<T>()
    for(i in 0 until this.length()){
        mutableList.add(c(this.getJSONObject(i)))
    }
    return mutableList
}

class RoomViewModel(application: Application, savedStateHandle: SavedStateHandle):AndroidViewModel(application) {


    private val id = savedStateHandle.get<Int>("id")!!

    var room: Room? by mutableStateOf(null)
        private set



    var isTyping by mutableStateOf<List<User>>(emptyList())



    private val socket = getApplication<TalkApplication>().socket


    private var recordingThread: Thread? = null

    private val sampleRate = 48000

    private var bufferSize = AudioRecord.getMinBufferSize(sampleRate,AudioFormat.CHANNEL_OUT_STEREO,AudioFormat.ENCODING_PCM_16BIT)




    private var recorder:AudioRecord? = null


    var toast by mutableStateOf("")
        private set


    var leavingAlert by mutableStateOf(false)
        private set

    var shouldLeave by mutableStateOf(false)
        private set



    private val audioTrack = AudioTrack(
        AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build(),
        AudioFormat.Builder().setEncoding(AudioFormat.ENCODING_PCM_16BIT).setSampleRate(sampleRate).setChannelMask(AudioFormat.CHANNEL_OUT_STEREO).build(),
        bufferSize,
        AudioTrack.MODE_STREAM,
        AudioManager.AUDIO_SESSION_ID_GENERATE
    ).also {
        it.play()
    }




    fun startRecording() {
        isMuted = false
        recorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_STEREO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        recorder!!.startRecording()
        recordingThread = Thread({ streamVoice() }, "AudioRecorder Thread")
        recordingThread!!.start()
    }


    fun stopRecording(){
        isMuted = true
        recorder!!.stop()
        recorder!!.release()
        recorder = null
        recordingThread = null
    }


    private fun streamVoice() {
        // Write the output audio in byte
        while(!isMuted){
            val sData = ByteArray(bufferSize * 2)
            recorder!!.read(sData, 0,bufferSize)
            socket.emit("voice",sData)
        }

    }


    val isReloading:Boolean
        get() = room == null






    private val onMembersChange =
        Emitter.Listener { args ->
            try {
                val data = args[0] as JSONArray

                room = room!!.copy(
                    members = room!!.members + data.getJSONObject(0).toUser()
                )
            } catch (err: Exception) {
                toast = err.message.toString()
            }
        }


    private val onVoice =
        Emitter.Listener { args ->
            try {
                val byteArray = args[0] as ByteArray
                audioTrack.write(byteArray,0,bufferSize)
            }catch (error:Exception){
                toast = error.message.toString()
            }
        }



    private val onNewMessage =
        Emitter.Listener { args ->
            try {
                val data = args[0] as JSONObject
                messages.add(data.toMessage())
            } catch (err: Exception) {
                toast = Thread.currentThread().name
            }
        }


    init {
        viewModelScope.launch {
            val response = getApplication<TalkApplication>().repository.joinRoom(id)
            if(response.isSuccessful){
                room = response.body()
                socket.emit("join",id)
                socket.on("members",onMembersChange)
                socket.on("message",onNewMessage)
                socket.on("voice",onVoice)
            }else shouldLeave = true
        }
    }


    override fun onCleared() {
        audioTrack.stop()
        audioTrack.release()
        recorder?.stop()
        recorder?.release()
        socket.off("members",onMembersChange)
        socket.off("message",onNewMessage)
        socket.off("voice")
        socket.emit("leave")
    }

    val messages = mutableStateListOf<Message>()


    var isMuted by mutableStateOf(true)
        private set



    var isListening by mutableStateOf(true)
        private set

    var input by mutableStateOf("")
        private set


    fun onInputChange(input:String){
        this.input = input
    }

    fun onIsListeningChange(isListening:Boolean){
        if(isListening) {
            socket.on("voice", onVoice)
            this.isListening = true
        }else{
            socket.off("voice")
            this.isListening = false
        }
    }

    fun sendMessage(){
        socket.emit("message",input.trim())
        input = ""
    }

    fun onToastDone(){
        toast = ""
    }

    fun onLeave(){
        leavingAlert = true
    }


    fun onDismissLeave(){
        leavingAlert = false
    }

    fun onConfirmLeave(){
        leavingAlert = false
        shouldLeave = true
    }
}

