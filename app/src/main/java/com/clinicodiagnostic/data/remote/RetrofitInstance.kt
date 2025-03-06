package com.clinicodiagnostic.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    fun getRetrofitInstance(baseUrl: String, retryAttempt: Int) : RetrofitService? {

        return try {

            val loggingInterceptor = try {
               LoggingInterceptor()
            } catch (e: Exception){
                e.printStackTrace()
                null
            }

            val retryInterceptor = try {
                RetryInterceptor(retryAttempt)
            } catch (e: Exception){
                e.printStackTrace()
                null
            }

            val clientBuilder = OkHttpClient.Builder()
            loggingInterceptor?.let {
                clientBuilder.addInterceptor(it)
            }
            retryInterceptor?.let {
                clientBuilder.addInterceptor(it)
            }

            val client = clientBuilder.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            retrofit.create(RetrofitService::class.java)

        } catch (e: Exception){
            e.printStackTrace()
           return null
        }
    }
}