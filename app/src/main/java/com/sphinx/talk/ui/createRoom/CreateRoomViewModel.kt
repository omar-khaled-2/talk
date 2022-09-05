package com.sphinx.talk.ui.createRoom

import android.app.Application
import android.view.textclassifier.TextLanguage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sphinx.talk.TalkApplication
import com.sphinx.talk.api.LanguageAndRoomCount
import com.sphinx.talk.api.Room
import kotlinx.coroutines.launch

class CreateRoomViewModel(application: Application):AndroidViewModel(application) {
    var topic by mutableStateOf("")
        private set

    var limit by mutableStateOf(0)
        private set
    var language by mutableStateOf("")
        private set

    var level by mutableStateOf("")
        private set

    var isCreated by mutableStateOf<Int?>(null)
        private set

    var languages by mutableStateOf<List<LanguageAndRoomCount>>(emptyList())
        private set


    val levels:List<String>
        get() = com.sphinx.talk.levels


    val canSubmit:Boolean
        get() = topic.isNotBlank() && limit > 1 && language.isNotBlank()


    init {
        viewModelScope.launch {
            val response = getApplication<TalkApplication>().repository.languages()
            languages = response.body()!!
        }
    }

    fun onTopicChange(topic:String){
        this.topic = topic
    }

    fun onLimitChange(limit:Int){
        this.limit = limit
    }

    fun onLanguageChange(language: String){
        this.language = language
    }

    fun onLevelChange(level:String){
        this.level = level
    }

    fun onCreateAlertDone(){
        isCreated = null
    }

    fun submit(){
        viewModelScope.launch {
            val response = getApplication<TalkApplication>().repository.createRoom(topic, language,level,limit)
            if(response.isSuccessful) isCreated = response.body()
        }
    }
}