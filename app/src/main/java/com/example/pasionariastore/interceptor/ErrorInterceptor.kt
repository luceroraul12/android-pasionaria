package com.example.pasionariastore.interceptor

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class ErrorInterceptor @Inject constructor() : Interceptor {
    val errorFlow = MutableSharedFlow<Pair<Int, String>>()
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = getResponse(chain = chain)
        handleErrors(response)
        return response
    }

    fun getResponse(chain: Interceptor.Chain): Response {
        val response =
            try {
                chain.proceed(chain.request())
            } catch (e: Exception) {
                Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .code(504) // Gateway Timeout
                    .message("SIN INTERNET")
                    .body(ResponseBody.create(null, "SIN INTERNET"))
                    .build()
            }
        return response
    }

    fun handleErrors(response: Response) {
        val okHttpScope = CoroutineScope(Job() + Dispatchers.IO)
        if (!response.isSuccessful) {
            Log.i("interceptors", "error no es exitoso emito mensaje")
            okHttpScope.launch {
                errorFlow.emit(Pair(response.code(), response.message()))
            }
        }
    }
}