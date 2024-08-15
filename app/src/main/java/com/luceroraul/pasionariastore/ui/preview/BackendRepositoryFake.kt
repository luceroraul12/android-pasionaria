package com.luceroraul.pasionariastore.ui.preview

import com.luceroraul.pasionariastore.model.BackendCart
import com.luceroraul.pasionariastore.model.BackendLogin
import com.luceroraul.pasionariastore.model.BackendLoginResponse
import com.luceroraul.pasionariastore.model.BackendProduct
import com.luceroraul.pasionariastore.repository.BackendRepository

class BackendRepositoryFake : BackendRepository {
    override suspend fun getCustomerProducts(): List<BackendProduct>? {
        TODO("Not yet implemented")
    }

    override suspend fun login(data: BackendLogin): BackendLoginResponse? {
        TODO("Not yet implemented")
    }

    override suspend fun synchronizeCarts(convertedCarts: List<BackendCart>): List<BackendCart>? {
        TODO("Not yet implemented")
    }
}