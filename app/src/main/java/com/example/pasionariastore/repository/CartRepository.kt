package com.example.pasionariastore.repository

import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import com.example.pasionariastore.room.CartDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CartRepository @Inject constructor(private val cartDatabaseDao: CartDatabaseDao) {

    fun getProducts(): Flow<List<ProductCartWithData>> {
        return cartDatabaseDao.getCartProducts().flowOn(Dispatchers.IO).conflate()
    }

    suspend fun insertProductCart(productCart: ProductCart) {
        cartDatabaseDao.insertCartProduct(productCart)
    }

    suspend fun deleteProductCart(productCart: ProductCart) {
        cartDatabaseDao.deleteProductCart(productCart)
    }

}