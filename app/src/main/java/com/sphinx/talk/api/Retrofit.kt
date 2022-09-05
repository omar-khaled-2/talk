package com.sphinx.talk.api

import com.sphinx.talk.Constant
import com.sphinx.talk.SessionManager
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}

class RetrofitInstances(private val sessionManager: SessionManager){
    private val retrofit by lazy {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(AuthInterceptor(sessionManager))

        Retrofit.Builder()
            .baseUrl(Constant.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    val roomApi by lazy {
        retrofit.create(RoomApi::class.java)
    }

    val authenticationApi by lazy {
        retrofit.create(AuthenticationApi::class.java)
    }
}
