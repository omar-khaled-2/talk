package com.sphinx.talk.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

data class CreateRoomBody(
    val topic:String,
    val language:String,
    val level:String,
    val limit:Int
)

data class UpdateEmailBody(
    val email: String,
    val password:String
)


interface RoomApi {
    @POST(Urls.ROOMS)
    suspend fun createRoom(
        @Body body:CreateRoomBody
    ):Response<Int>

    @GET(Urls.ROOMS)
    suspend fun fetchRooms(
        @Query("topic") topic: String,
        @Query("language") language: String?,
        @Query("level") level: String?,
    ):Response<List<Room>>


    @GET(Urls.LANGUAGES)
    suspend fun languages():Response<List<LanguageAndRoomCount>>

    @GET("${Urls.ROOMS}{id}")
    suspend fun joinRoom(
        @Path("id") id:Int
    ):Response<Room>

    @GET(Urls.EMAIL)
    suspend fun fetchEmail():Response<String>



    @PUT(Urls.EMAIL)
    suspend fun updateEmail(
        @Body body:UpdateEmailBody
    ):Response<Unit>



    @DELETE(Urls.USER)
    suspend fun deleteAccount():Response<Unit>



    @Multipart
    @PUT(Urls.USER)
    suspend fun updateUser(
        @Part("firstName") firstName:RequestBody,
        @Part("lastName") lastName:RequestBody,
        @Part avatar:MultipartBody.Part,
    ):Response<User>
}