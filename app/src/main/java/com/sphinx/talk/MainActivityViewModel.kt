package com.sphinx.talk

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sphinx.talk.api.Room
import com.sphinx.talk.api.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject




class MainActivityViewModel(application: Application):AndroidViewModel(application) {



    init {
        viewModelScope.launch{
            val response = getApplication<TalkApplication>().authentication.fetchUser()
            if(response.isSuccessful) onUserChange(response.body())
        }
    }

    var user by mutableStateOf<User?>(null)
        private set


    var isDark by mutableStateOf(false)

    fun onUserChange(user:User?){
        this.user = user
    }

    fun onIsDarkChange(isDark:Boolean){
        this.isDark = isDark
    }
}