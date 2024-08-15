package com.luceroraul.pasionariastore.repository

import com.luceroraul.pasionariastore.model.Product
import com.luceroraul.pasionariastore.model.ProductWithUnit
import com.luceroraul.pasionariastore.model.Unit
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductsWithUnit(): Flow<List<ProductWithUnit>>
    fun getProducts(): Flow<List<Product>>
    fun getProductsWithUnitBySearch(search: String, cartId: Long): Flow<List<ProductWithUnit>>
    fun getProductsWithUnitById(id: Long): Flow<ProductWithUnit>
    fun getUnits(): Flow<List<Unit>>
    suspend fun saveFirstUnits()
    suspend fun saveFirstProducts()
    suspend fun saveUnits(units: List<Unit>)
    suspend fun saveProducts(products: List<Product>)
}