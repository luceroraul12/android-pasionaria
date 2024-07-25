package com.example.pasionariastore.repository

import com.example.pasionariastore.room.ProductDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDatabaseDao: ProductDatabaseDao) {

    fun getProductsBySearch(search: String) =
        productDatabaseDao.getProductsBySearch("%$search%").flowOn(Dispatchers.IO).conflate()

}