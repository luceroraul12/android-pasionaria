package com.luceroraul.pasionariastore.repository

import com.luceroraul.pasionariastore.model.Cart
import com.luceroraul.pasionariastore.model.CartWithData
import com.luceroraul.pasionariastore.model.Product
import com.luceroraul.pasionariastore.model.ProductCart
import com.luceroraul.pasionariastore.model.ProductCartWithData
import com.luceroraul.pasionariastore.room.CartDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(private val cartDatabaseDao: CartDatabaseDao) :
    CartRepository {
    override fun getCartWithData(cartId: Long): Flow<CartWithData> {
        return cartDatabaseDao.getCartWithData(cartId).flowOn(Dispatchers.IO).conflate()
    }

    override fun getProducts(): Flow<List<ProductCartWithData>> {
        return cartDatabaseDao.getCartProducts().flowOn(Dispatchers.IO).conflate()
    }

    override fun getCartsWithStatus(status: List<String>): Flow<List<CartWithData>> {
        return cartDatabaseDao.getCartsWithStatus(status).flowOn(Dispatchers.IO).conflate()
    }

    override fun getCartProductWithDataById(productCartId: Long): Flow<ProductCartWithData> {
        return cartDatabaseDao.getProductCartWithDataById(productCartId).flowOn(Dispatchers.IO)
            .conflate()
    }

    override fun getTopProducts(): Flow<List<Product>> {
        return cartDatabaseDao.getTopProducts().flowOn(Dispatchers.IO)
            .conflate()
    }

    override suspend fun insertProductCart(productCart: ProductCart) {
        cartDatabaseDao.insertCartProduct(productCart)
    }

    override suspend fun updateProductCart(productCart: ProductCart) {
        cartDatabaseDao.updateCartProduct(productCart)

    }

    override suspend fun deleteProductCart(productCart: ProductCart) {
        cartDatabaseDao.deleteProductCart(productCart)
    }

    override suspend fun insertCart(cart: Cart) {
        cartDatabaseDao.insertCart(cart)
    }

    override suspend fun updateCart(cart: Cart) {
        cartDatabaseDao.updateCart(cart)
    }

    override suspend fun deleteCart(cart: Cart) {
        cartDatabaseDao.deleteCart(cart)
    }

    override fun updateCartIdsAndStatus(cartId: Long, backendCartId: Long, status: String) {
        cartDatabaseDao.updateCartIdsAndStatus(
            cartId = cartId,
            backendCartId = backendCartId,
            status = status
        )
    }

    override fun updateCartProductIds(cartProductId: Long, backendCartProductId: Long) {
        cartDatabaseDao.updateCartProductIds(
            cartProductId = cartProductId,
            backendCartProductId = backendCartProductId
        )
    }

    override fun updateProductCarts(cartProductsToUpdate: MutableList<ProductCart>) {
        cartDatabaseDao.updateCartProducts(cartProductsToUpdate)
    }

}