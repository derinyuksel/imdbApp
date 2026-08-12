package com.example.imdbapp.core

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader(
                name = "Authorization",
                value = "Bearer ${"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZTY1YWJhY2FjYjg5ZjZlZmQ3OGJiMDQxMzFlNGIzMyIsIm5iZiI6MTc4NTkyNjI1MS43NDMsInN1YiI6IjZhNzMxMjZiYjRlNjMwYmEzN2MyN2NjNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PKY4GO8Wit6Oe-7hC9AxYETU5bvmTkvpBdPneqnU95s"}"
            )
            .addHeader(
                name = "Accept",
                value = "application/json"
            )
            .build()

        return chain.proceed(request)
    }
}