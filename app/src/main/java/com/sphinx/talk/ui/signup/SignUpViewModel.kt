package com.sphinx.talk.ui.signup

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


class SignUpViewModel(application: Application):AndroidViewModel(application) {
    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set


    var user by mutableStateOf<User?>(null)
        private set

    var toast by mutableStateOf("")
        private set





    fun onFirstNameChange(firstName:String){
        this.firstName = firstName
    }


    fun onLastNameChange(lastName:String){
        this.lastName = lastName
    }


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
                val response = getApplication<TalkApplication>().authentication.signUp(
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    password = password
                )
                user = response.body()!!.user
            }catch (error:Exception){
                toast = error.message ?: "error"
            }


        }
    }

}