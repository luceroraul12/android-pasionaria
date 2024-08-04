package com.example.pasionariastore.repository

import android.util.Log
import com.example.pasionariastore.data.api.ApiBackend
import com.example.pasionariastore.model.BackendProduct
import javax.inject.Inject

class BackendRepository @Inject constructor(
    private val apiBackend: ApiBackend
) {

    suspend fun getCustomerProducts(): List<BackendProduct>? {
        try {
            val response = apiBackend.getCustomerProducts()
            if (response.isSuccessful)
                return response.body() else
                return null
        } catch (e: Exception){
            Log.i("backend", e.message!!)
            return null
        }

    }
}