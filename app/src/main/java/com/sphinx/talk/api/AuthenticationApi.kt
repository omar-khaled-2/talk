package com.sphinx.talk.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class SignResponse(
    val user:User,
    val token:String
)


data class SignUpBody(
    val email:String,
    val firstName:String,
    val lastName : String,
    val password:String
)

data class SignInBody(
    val email: String,
    val password: String
)

interface AuthenticationApi {
    @POST(Urls.SIGN_UP)
    suspend fun signUp(
        @Body body:SignUpBody
    ):Response<SignResponse>

    @POST(Urls.SIGN_IN)
    suspend fun signIn(
        @Body body: SignInBody
    ):Response<SignResponse>

    @GET(Urls.USER)
    suspend fun fetchUser():Response<User>
}