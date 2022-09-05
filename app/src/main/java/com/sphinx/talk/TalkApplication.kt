package com.sphinx.talk

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.sphinx.talk.api.Authentication
import com.sphinx.talk.api.Repository
import com.sphinx.talk.api.RetrofitInstances
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.WebSocket
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SessionManager(context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secret_shared_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )



    companion object {
        const val USER_TOKEN = "user_token"
        private var instance:SessionManager? = null

        fun getInstance(context: Context):SessionManager{
            if(SessionManager.instance === null) instance = SessionManager(context)
            return instance!!
        }

    }

    private val editor by lazy {
        sharedPreferences.edit()
    }

    fun saveAuthToken(token: String) {
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }


    fun fetchAuthToken(): String? {
        return sharedPreferences.getString(USER_TOKEN, null)
    }

    fun deleteAuthToken(){
        editor.remove(USER_TOKEN).apply()
    }
}

class TalkApplication:Application() {



    val sessionManager by lazy { SessionManager(this.applicationContext) }

    private val retrofit:RetrofitInstances by lazy {
        RetrofitInstances(sessionManager)
    }



    val repository by lazy { Repository(
        retrofit.roomApi
    ) }

    val authentication by lazy {
        Authentication(retrofit.authenticationApi)
    }


    val socket: Socket by lazy {
        IO.socket(Constant.WS_URL,IO.Options.builder().setAuth(
            mapOf(
                "token" to sessionManager.fetchAuthToken()
            )
        ).build()).connect()

    }

}