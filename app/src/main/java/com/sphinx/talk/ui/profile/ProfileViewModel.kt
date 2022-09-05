package com.sphinx.talk.ui.profile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.sphinx.talk.api.User








class ProfileViewModel(application: Application):AndroidViewModel(application) {
    private var user:User? by mutableStateOf(null)


    var isLoved:Boolean? by mutableStateOf(null)
        private set

    var loversCount:Int by mutableStateOf(0)
        private set


    val name:String
        get() = user?.name ?: ""


    val avatar:String?
        get() = user?.avatar

    val isReloading:Boolean
        get() = user == null


    fun love(){
        isLoved = true
    }

    fun unLove(){
        isLoved = false
    }


}