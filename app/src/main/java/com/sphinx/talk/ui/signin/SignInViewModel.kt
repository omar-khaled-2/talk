package com.sphinx.talk.ui.signin

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sphinx.talk.TalkApplication
import com.sphinx.talk.api.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(application: Application):AndroidViewModel(application) {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var toast by mutableStateOf("")
        private set

    var user by mutableStateOf<User?>(null)
        private set

    fun onEmailChange(email:String){
        this.email = email
    }

    fun onPasswordChange(password:String){
        this.password = password
    }

    fun onToastEnd(){
        toast = ""
    }

    fun submit(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getApplication<TalkApplication>().authentication.signIn(email,password)
                user = response.body()!!.user
                getApplication<TalkApplication>().sessionManager.saveAuthToken(response.body()!!.token)
            }catch (error:Exception){
                toast = error.message ?: "error"
            }


        }
    }
}