package com.example.pasionariastore.repository

import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.ProductWithUnit
import com.example.pasionariastore.room.ProductDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDatabaseDao: ProductDatabaseDao) {
    fun getProductsWithUnit(): Flow<List<ProductWithUnit>?> =
        productDatabaseDao.getProductsWithUnit().flowOn(Dispatchers.IO).conflate()

    fun getProductsWithUnitBySearch(search: String) =
        productDatabaseDao.getProductsBySearch("%$search%").flowOn(Dispatchers.IO).conflate()

    fun getProductsWithUnitById(id: Long) {
        productDatabaseDao.getProductsWithUnitById(id).flowOn(Dispatchers.IO).conflate()
    }

    fun getUnits() = productDatabaseDao.getUnits().flowOn(Dispatchers.IO).conflate()

    // Auxiliares
    suspend fun saveFirstUnits() {
        productDatabaseDao.insertUnits(Datasource.apiUnits)
    }

    suspend fun saveFirstProducts() {
        // Creo los productos
        productDatabaseDao.insertProducts(Datasource.apiProducts)
        // Los recupero y creo los WithUnit
//        productDatabaseDao.getProducts().collect(
//            productDatabaseDao.insert
//        )
    }

}