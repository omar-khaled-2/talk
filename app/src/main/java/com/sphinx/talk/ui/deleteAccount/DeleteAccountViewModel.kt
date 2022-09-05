package com.sphinx.talk.ui.deleteAccount

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sphinx.talk.TalkApplication
import kotlinx.coroutines.launch

class DeleteAccountViewModel(application: Application):AndroidViewModel(application) {
    var password by mutableStateOf("")
        private set

    var isDeleted by mutableStateOf(false)
        private set

    var toast by mutableStateOf("")
        private set


    var alert by mutableStateOf(false)
        private set


    fun onPasswordChange(password:String){
        this.password = password
    }

    fun onToastDone(){
        toast = ""
    }

    fun deleteAccount(){
        viewModelScope.launch {
            alert = false
            val response = getApplication<TalkApplication>().repository.deleteAccount()
            if(response.isSuccessful) isDeleted = true

        }
    }

    fun showAlert(){
        alert = true
    }

    fun dismissAlert(){
        alert = false
    }

}