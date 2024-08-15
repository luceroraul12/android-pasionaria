package com.luceroraul.pasionariastore.repository

import com.luceroraul.pasionariastore.model.BackendCart
import com.luceroraul.pasionariastore.model.BackendLogin
import com.luceroraul.pasionariastore.model.BackendLoginResponse
import com.luceroraul.pasionariastore.model.BackendProduct

interface BackendRepository {
    suspend fun getCustomerProducts(): List<BackendProduct>?
    suspend fun login(data: BackendLogin): BackendLoginResponse?
    suspend fun synchronizeCarts(convertedCarts: List<BackendCart>): List<BackendCart>?
}
