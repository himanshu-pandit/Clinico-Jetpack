package com.clinicodiagnostic.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class RetryInterceptor(private val retryAttempts: Int): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        for (i in 1..retryAttempts){
            try {
                return chain.proceed(chain.request())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        throw RuntimeException("failed to compile the request")
    }
}