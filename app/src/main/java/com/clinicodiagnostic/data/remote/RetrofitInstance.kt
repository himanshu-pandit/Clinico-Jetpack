package com.clinicodiagnostic.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    fun getRetrofitInstance(baseUrl: String, retryAttempt: Int) : RetrofitService{

        val loggingInterceptor = LoggingInterceptor()
        val retryInterceptor = RetryInterceptor(retryAttempt)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(retryInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(RetrofitService::class.java)
    }
}