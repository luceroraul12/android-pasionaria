package com.example.pasionariastore.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class BackendInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request().newBuilder()
        val newRequest = currentRequest.build()
        return chain.proceed(newRequest)
    }
}