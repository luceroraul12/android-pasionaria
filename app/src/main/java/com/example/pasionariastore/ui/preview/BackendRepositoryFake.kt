package com.example.pasionariastore.ui.preview

import com.example.pasionariastore.model.BackendLogin
import com.example.pasionariastore.model.BackendLoginResponse
import com.example.pasionariastore.model.BackendProduct
import com.example.pasionariastore.repository.BackendRepository

class BackendRepositoryFake : BackendRepository {
    override suspend fun getCustomerProducts(): List<BackendProduct>? {
        TODO("Not yet implemented")
    }

    override suspend fun login(data: BackendLogin): BackendLoginResponse? {
        TODO("Not yet implemented")
    }
}