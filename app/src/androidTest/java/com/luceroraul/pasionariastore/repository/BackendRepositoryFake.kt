package com.luceroraul.pasionariastore.repository

import com.luceroraul.pasionariastore.model.BackendCart
import com.luceroraul.pasionariastore.model.BackendLogin
import com.luceroraul.pasionariastore.model.BackendLoginResponse
import com.luceroraul.pasionariastore.model.BackendLookup
import com.luceroraul.pasionariastore.model.BackendProduct

class BackendRepositoryFake : BackendRepository {
    override suspend fun getCustomerProducts(): List<BackendProduct>? {
        return listOf(
            BackendProduct(
                unitType = BackendLookup(
                    code = "MEDIDAS_VENTAS_1U",
                    description = "Unidad"
                )
            ),
            BackendProduct(

                unitType = BackendLookup(
                    code = "MEDIDAS_VENTAS_100G",
                    description = "x100gr"
                )
            ),
            BackendProduct(
                unitType = BackendLookup(
                    code = "MEDIDAS_VENTAS_50G",
                    description = "x50gr"
                )
            )
        )
    }

    override suspend fun login(data: BackendLogin): BackendLoginResponse? {
        TODO("Not yet implemented")
    }

    override suspend fun synchronizeCarts(convertedCarts: List<BackendCart>): List<BackendCart>? {
        TODO("Not yet implemented")
    }
}