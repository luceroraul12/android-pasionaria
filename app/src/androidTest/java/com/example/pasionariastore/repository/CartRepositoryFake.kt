package com.example.pasionariastore.repository

import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import kotlinx.coroutines.flow.Flow

class CartRepositoryFake : CartRepository {
    override fun getProducts(): Flow<List<ProductCartWithData>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertProductCart(productCart: ProductCart) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProductCart(productCart: ProductCart) {
        TODO("Not yet implemented")
    }
}