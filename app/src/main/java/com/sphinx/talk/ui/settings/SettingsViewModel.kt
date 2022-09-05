package com.sphinx.talk.ui.settings

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.sphinx.talk.TalkApplication

class SettingsViewModel(application: Application):AndroidViewModel(application) {
    var isLoggedOut by mutableStateOf(false)

    fun logout(){
        getApplication<TalkApplication>().sessionManager.deleteAuthToken()
        isLoggedOut = true
    }
}