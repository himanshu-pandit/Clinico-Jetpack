package com.clinicodiagnostic.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class RetryInterceptor(private val retryAttempts: Int): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var lastException: Exception? = null

        for (i in 1..retryAttempts){
            try {
                return chain.proceed(chain.request())
            }catch (e: Exception){
                lastException = e
                e.printStackTrace()
            }
        }
        throw lastException ?: RuntimeException("Failed after $retryAttempts retry attempts.")
    }
}