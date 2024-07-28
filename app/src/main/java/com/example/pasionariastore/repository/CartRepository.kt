package com.example.pasionariastore.repository

import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartWithData(cartId: Long): Flow<CartWithData>
    fun getProducts(): Flow<List<ProductCartWithData>>
    fun getCartsWithStatus(status: List<String>): Flow<List<Cart>>
    suspend fun insertProductCart(productCart: ProductCart)
    suspend fun deleteProductCart(productCart: ProductCart)
    suspend fun insertCart(cart: Cart)
    suspend fun deleteCart(cart: Cart)
}