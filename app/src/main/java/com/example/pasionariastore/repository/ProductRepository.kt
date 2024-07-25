package com.example.pasionariastore.repository

import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.room.ProductDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDatabaseDao: ProductDatabaseDao) {
    fun getProductsWithUnit() =
        productDatabaseDao.getProductsWithUnit().flowOn(Dispatchers.IO).conflate()

    fun getProductsWithUnitBySearch(search: String) =
        productDatabaseDao.getProductsBySearch("%$search%").flowOn(Dispatchers.IO).conflate()

    fun getProducts() = productDatabaseDao.getProducts().flowOn(Dispatchers.IO).conflate()

    fun getUnits() = productDatabaseDao.getUnits().flowOn(Dispatchers.IO).conflate()

    // Auxiliares
    suspend fun saveFirstUnits() {
        productDatabaseDao.insertUnits(Datasource.apiUnits)
    }

    suspend fun saveFirstProducts() {
        productDatabaseDao.insertProducts(Datasource.apiProducts)
    }

}