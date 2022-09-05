package com.sphinx.talk.api



data class Room(
    val id:Int,
    val topic:String,
    val language: String,
    val level:String,
    val limit:Int,
    val members:List<User>
)


