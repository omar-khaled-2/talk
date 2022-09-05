package com.sphinx.talk.api

import android.util.Log
import com.sphinx.talk.Constant

data class User(
    val id:Int,
    val firstName:String,
    val lastName:String,
    val avatar:String?,
){
    val name:String
        get() = "$firstName $lastName"
}

