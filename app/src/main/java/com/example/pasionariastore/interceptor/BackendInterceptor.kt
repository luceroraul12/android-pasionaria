package com.example.pasionariastore.interceptor

import androidx.compose.runtime.collectAsState
import com.example.pasionariastore.data.CustomDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BackendInterceptor @Inject constructor(
    private val customDataStore: CustomDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Creo corrutina
        val okHttpScope = CoroutineScope(Job() + Dispatchers.IO)
        val response = runBlocking {
            okHttpScope.async {
                val token = customDataStore.getToken().first()

                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }.await()
        }

        return response
    }
}