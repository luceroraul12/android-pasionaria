package com.example.pasionariastore.repository

import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import com.example.pasionariastore.room.CartDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CartRepositoryImpl @Inject constructor(private val cartDatabaseDao: CartDatabaseDao) :
    CartRepository {

    override fun getProducts(): Flow<List<ProductCartWithData>> {
        return cartDatabaseDao.getCartProducts().flowOn(Dispatchers.IO).conflate()
    }

    override suspend fun insertProductCart(productCart: ProductCart) {
        cartDatabaseDao.insertCartProduct(productCart)
    }

    override suspend fun deleteProductCart(productCart: ProductCart) {
        cartDatabaseDao.deleteProductCart(productCart)
    }

}