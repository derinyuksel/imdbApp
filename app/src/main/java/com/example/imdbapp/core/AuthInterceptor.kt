package com.example.imdbapp.core

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder
            .addHeader(
                name = "Authorization",
                value = "Bearer ${BuildConfig.API_KEY}"
            )
            .addHeader(
                name = "Accept",
                value = "application/json"
            )
            .build()

        return chain.proceed(request)
    }
}