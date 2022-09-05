package com.sphinx.talk.ui.home

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sphinx.talk.TalkApplication
import com.sphinx.talk.api.Room
import com.sphinx.talk.api.User
import kotlinx.coroutines.launch


class HomeViewModel(application: Application):AndroidViewModel(application) {
    val rooms = mutableStateListOf<Room>()
    var isReloading by mutableStateOf(true)
        private set

    var filter by mutableStateOf(Filter())
        private set

    var toast by mutableStateOf("")
        private set


    var showMenu by mutableStateOf<Room?>(null)
        private set

    init {
        search()
    }


    fun showMenu(room:Room){
        showMenu = room
    }

    fun dismissMenu(){
        showMenu = null
    }

    private fun search(){
        viewModelScope.launch {
            isReloading = true
            try {
                val response = getApplication<TalkApplication>().repository.fetchRooms(filter)
                if(response.isSuccessful){
                    rooms.clear()
                    rooms.addAll(response.body()!!)
                }

                isReloading = false
            }catch (error:Exception){
                toast = error.message ?: ""
            }


        }
    }

    fun onFilterChange(filter: Filter){
        this.filter = filter
        search()
    }

    fun report(id:Int){

    }

    fun refresh() = search()

    fun onToastDone(){
        toast = ""
    }
}