package com.sphinx.talk.api

import retrofit2.Retrofit
import java.io.File
import android.R.id
import android.util.Log
import com.sphinx.talk.ui.home.Filter
import okhttp3.MediaType

import okhttp3.RequestBody

import okhttp3.MultipartBody
import retrofit2.Response
import okhttp3.ResponseBody





class Repository(private val roomApi: RoomApi) {
    suspend fun createRoom(topic:String,language:String,level:String,limit:Int) = roomApi.createRoom(
        CreateRoomBody(
            topic,
            language,
            level,
            limit
        )
    )

    suspend fun fetchRooms(filter:Filter) = roomApi.fetchRooms(filter.topic,filter.language,filter.level)

    suspend fun fetchEmail() = roomApi.fetchEmail()

    suspend fun joinRoom(id:Int) = roomApi.joinRoom(id)


    suspend fun languages() = roomApi.languages()
    suspend fun updateEmail(email: String,password: String) = roomApi.updateEmail(UpdateEmailBody(email, password))


    suspend fun deleteAccount() = roomApi.deleteAccount()


    suspend fun updateUser(firstName: String,lastName:String,avatar:File): Response<User> {


        val requestFile = RequestBody.create(
            MediaType.parse(avatar.extension),
            avatar
        )

        val body = MultipartBody.Part.createFormData("avatar", avatar.name, requestFile)

        val firstName = RequestBody.create(
            MultipartBody.FORM, firstName
        )

        val lastName = RequestBody.create(
            MultipartBody.FORM, lastName
        )

        return roomApi.updateUser(firstName,lastName, body)

    }
}




class Authentication(private val authenticationApi: AuthenticationApi){
    suspend fun signIn(email:String,password:String) = authenticationApi.signIn(SignInBody(email, password))
    suspend fun signUp(email: String,firstName:String,lastName:String,password: String) = authenticationApi.signUp(
        SignUpBody(email, firstName,lastName, password)
    )
    suspend fun fetchUser() = authenticationApi.fetchUser()
}