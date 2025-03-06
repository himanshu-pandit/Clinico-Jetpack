package com.clinicodiagnostic.data.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.logging.Logger

class LoggingInterceptor: Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {

        val logger = Logger.getLogger("MyLogger")

        val request:Request = chain.request()

        val t1 = System.currentTimeMillis()
        logger.info(
            String.format(
                "Sending request %s on %s%n%s",
                request.url,
                chain.connection(),
                request.headers
            )
        )

        val response: Response = chain.proceed(request)

        val t2 = System.currentTimeMillis()
        logger.info(
            String.format(
                "Received response for %s in %.1fms%n%s",
                response.request.url,
                (t2-t1) / 1e6,
                response.headers
            )
        )

        return response
    }
}