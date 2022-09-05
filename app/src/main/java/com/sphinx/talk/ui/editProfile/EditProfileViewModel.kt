package com.sphinx.talk.ui.editProfile

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sphinx.talk.Constant
import com.sphinx.talk.TalkApplication
import com.sphinx.talk.api.User
import com.sphinx.talk.getFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream





class EditProfileViewModel(application: Application):AndroidViewModel(application) {

    var isSaved by mutableStateOf<User?>(null)


    var firstName by mutableStateOf("")
        private set


    var lastName by mutableStateOf("")
        private set

    var avatar by mutableStateOf<String?>(null)


    var toast by mutableStateOf("")
        private set


    var isReloading by mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {

            val response = getApplication<TalkApplication>().authentication.fetchUser()

            if(response.isSuccessful){
                response.body()?.also {
                    firstName = it.firstName
                    lastName = it.lastName
                    if(it.avatar !== null) avatar = Constant.SERVER_URL + it.avatar
                }
            }

            isReloading = false
        }
    }
    fun onFirstNameChange(firstName:String){
        this.firstName = firstName
    }

    fun onLastNameChange(lastName:String){
        this.lastName = lastName
    }

    fun onAvatarChange(uri:Uri){
        avatar = uri.toString()
    }

    fun onToastDone(){
        toast = ""
    }

    fun save(){
        viewModelScope.launch(Dispatchers.IO) {
            isReloading = true
            val file = getApplication<Application>().contentResolver.getFile(avatar!!,getApplication<Application>().cacheDir.path,"1.jpg")

            val response = getApplication<TalkApplication>().repository.updateUser(firstName,lastName,file)
            if(response.isSuccessful) isSaved = response.body()
            else{
                isReloading = false

            }

        }


    }
//

}