package com.example.pasionariastore.repository

import android.util.Log
import com.example.pasionariastore.data.api.ApiBackend
import com.example.pasionariastore.model.BackendLogin
import com.example.pasionariastore.model.BackendLoginResponse
import com.example.pasionariastore.model.BackendProduct
import javax.inject.Inject

class BackendRepositoryImpl @Inject constructor(
    private val apiBackend: ApiBackend
) : BackendRepository {

    override suspend fun getCustomerProducts(): List<BackendProduct>? {
        try {
            val response = apiBackend.getCustomerProducts()
            if (response.isSuccessful)
                return response.body() else
                return null
        } catch (e: Exception) {
            Log.i("backendError", e.stackTraceToString())
            return null
        }
    }

    override suspend fun login(data: BackendLogin): BackendLoginResponse? {
        try {
            val response = apiBackend.login(data)
            if (response.isSuccessful)
                return response.body() else
                return null
        } catch (e: Exception) {
            Log.i("backendError", e.stackTraceToString())
            return null
        }
    }
}