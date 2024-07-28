package com.example.pasionariastore.repository

import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getProducts(): Flow<List<ProductCartWithData>>
    fun getCarts(): Flow<List<Cart>>
    suspend fun insertProductCart(productCart: ProductCart)
    suspend fun deleteProductCart(productCart: ProductCart)
    suspend fun insertCart(cart: Cart)
    suspend fun deleteCart(cart: Cart)
}