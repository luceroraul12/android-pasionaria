package com.luceroraul.pasionariastore.ui.preview

import com.luceroraul.pasionariastore.data.Datasource
import com.luceroraul.pasionariastore.model.Cart
import com.luceroraul.pasionariastore.model.CartWithData
import com.luceroraul.pasionariastore.model.Product
import com.luceroraul.pasionariastore.model.ProductCart
import com.luceroraul.pasionariastore.model.ProductCartWithData
import com.luceroraul.pasionariastore.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CartRepositoryFake : CartRepository {
    override fun getCartWithData(cartId: Long): Flow<CartWithData> =
        flow { Datasource.cartWithData }

    override fun getProducts(): Flow<List<ProductCartWithData>> = flow { Datasource.cartProducts }
    override fun getCartsWithStatus(status: List<String>): Flow<List<CartWithData>> =
        flow { Datasource.cartWithData }

    override suspend fun updateProductCart(productCart: ProductCart) {
        TODO("Not yet implemented")
    }

    override fun getCartProductWithDataById(productCartId: Long): Flow<ProductCartWithData> {
        TODO("Not yet implemented")
    }

    override fun getTopProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertProductCart(productCart: ProductCart) {}
    override suspend fun deleteProductCart(productCart: ProductCart) {}
    override suspend fun insertCart(cart: Cart) {}
    override suspend fun updateCart(cart: Cart) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCart(cart: Cart) {}
    override fun updateCartIdsAndStatus(cartId: Long, backendCartId: Long, status: String) {
        TODO("Not yet implemented")
    }

    override fun updateCartProductIds(cartProductId: Long, backendCartProductId: Long) {
        TODO("Not yet implemented")
    }

    override fun updateProductCarts(cartProductsToUpdate: MutableList<ProductCart>) {
        TODO("Not yet implemented")
    }
}