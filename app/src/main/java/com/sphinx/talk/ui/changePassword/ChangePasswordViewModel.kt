package com.sphinx.talk.ui.changePassword

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class ChangePasswordViewModel(application: Application):AndroidViewModel(application) {
    var newPassword by mutableStateOf("")
        private set

    var confirmNewPassword by mutableStateOf("")
        private set

    var currentPassword by mutableStateOf("")
        private set

    var isSaved by mutableStateOf(false)
        private set


    fun onNewPasswordChange(value:String){
        newPassword = value
    }

    fun onConfirmNewPasswordChange(value: String){
        confirmNewPassword = value
    }

    fun onCurrentPasswordChange(value:String){
        currentPassword = value
    }

    fun save(){

    }


}