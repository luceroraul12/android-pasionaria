package com.example.pasionariastore.repository

import com.example.pasionariastore.model.BackendProduct

interface BackendRepository {
    suspend fun getCustomerProducts(): List<BackendProduct>?
}
