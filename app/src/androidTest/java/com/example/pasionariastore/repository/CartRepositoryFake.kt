package com.example.pasionariastore.repository

import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CartRepositoryFake : CartRepository {
    override fun getProducts(): Flow<List<ProductCartWithData>> = flow { Datasource.cartProducts }
    override suspend fun insertProductCart(productCart: ProductCart) {}
    override suspend fun deleteProductCart(productCart: ProductCart) {}
}