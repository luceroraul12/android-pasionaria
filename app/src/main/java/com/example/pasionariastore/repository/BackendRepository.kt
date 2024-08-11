package com.example.pasionariastore.repository

import com.example.pasionariastore.model.BackendCart
import com.example.pasionariastore.model.BackendLogin
import com.example.pasionariastore.model.BackendLoginResponse
import com.example.pasionariastore.model.BackendProduct

interface BackendRepository {
    suspend fun getCustomerProducts(): List<BackendProduct>?
    suspend fun login(data: BackendLogin): BackendLoginResponse?
    suspend fun synchronizeCarts(convertedCarts: List<BackendCart>): List<BackendCart>?
}
