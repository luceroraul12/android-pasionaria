package com.example.pasionariastore.repository

import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getProducts(): Flow<List<ProductCartWithData>>

    suspend fun insertProductCart(productCart: ProductCart)

    suspend fun deleteProductCart(productCart: ProductCart)
}