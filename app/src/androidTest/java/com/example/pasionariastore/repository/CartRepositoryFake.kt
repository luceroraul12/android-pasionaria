package com.example.pasionariastore.repository

import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CartRepositoryFake : CartRepository {
    override fun getCartWithData(cartId: Long): Flow<CartWithData> {
        TODO("Not yet implemented")
    }

    override fun getProducts(): Flow<List<ProductCartWithData>> = flow { Datasource.cartProducts }
    override fun getCartsWithStatus(status: List<String>): Flow<List<Cart>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertProductCart(productCart: ProductCart) {}
    override suspend fun deleteProductCart(productCart: ProductCart) {}
    override suspend fun insertCart(cart: Cart) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCart(cart: Cart) {
        TODO("Not yet implemented")
    }
}