package com.sphinx.talk.ui.changeEmail

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sphinx.talk.TalkApplication
import kotlinx.coroutines.launch

class ChangeEmailViewModel(application: Application):AndroidViewModel(application) {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var isSaved by mutableStateOf(false)
        private set


    var toast by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            try {
                val response = getApplication<TalkApplication>().repository.fetchEmail()
                if(response.isSuccessful) email = response.body()!!
            }catch (error:Exception){

            }

        }
    }

    fun onEmailChange(email:String){
        this.email = email
    }

    fun onPasswordChange(password:String){
        this.password = password
    }

    fun onToastDone(){
        toast = ""
    }

    fun save(){
        viewModelScope.launch {
            try {
                val response = getApplication<TalkApplication>().repository.updateEmail(email,password)
                if(response.isSuccessful)  isSaved = true
                else toast = response.errorBody().toString()
            }catch (error:Exception){
                toast = error.message.toString()
            }
        }
    }
}