package com.example.pasionariastore.ui.preview

import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import com.example.pasionariastore.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CartRepositoryFake : CartRepository {
    override fun getCartWithData(cartId: Long): Flow<CartWithData> {
        TODO("Not yet implemented")
    }

    override fun getProducts(): Flow<List<ProductCartWithData>> = flow { Datasource.cartProducts }
    override fun getCartsWithStatus(status: List<String>): Flow<List<Cart>> = flow { Datasource.carts }

    override suspend fun insertProductCart(productCart: ProductCart) {}
    override suspend fun deleteProductCart(productCart: ProductCart) {}
    override suspend fun insertCart(cart: Cart) {}
    override suspend fun deleteCart(cart: Cart) {}
}