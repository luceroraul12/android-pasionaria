package com.example.pasionariastore.repository

import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartWithData(cartId: Long): Flow<CartWithData?>
    fun getProducts(): Flow<List<ProductCartWithData>>
    fun getCartsWithStatus(status: List<String>): Flow<List<CartWithData>>
    fun getCartProductWithDataById(productCartId: Long): Flow<ProductCartWithData?>
    suspend fun insertProductCart(productCart: ProductCart)
    suspend fun updateProductCart(productCart: ProductCart)
    suspend fun deleteProductCart(productCart: ProductCart)
    suspend fun insertCart(cart: Cart)
    suspend fun updateCart(cart: Cart)
    suspend fun deleteCart(cart: Cart)
    fun updateCartIdsAndStatus(cartId: Long, backendCartId: Long, status: String)
    fun updateCartProductIds(cartProductId: Long, backendCartProductId: Long)
}