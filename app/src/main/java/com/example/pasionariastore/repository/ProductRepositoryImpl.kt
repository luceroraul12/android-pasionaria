package com.example.pasionariastore.repository

import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductWithUnit
import com.example.pasionariastore.model.Unit
import com.example.pasionariastore.room.ProductDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(private val productDatabaseDao: ProductDatabaseDao) :
    ProductRepository {
    override fun getProductsWithUnit(): Flow<List<ProductWithUnit>> =
        productDatabaseDao.getProductsWithUnit().flowOn(Dispatchers.IO).conflate()

    override fun getProducts(): Flow<List<Product>> {
        return productDatabaseDao.getProducts().flowOn(Dispatchers.IO).conflate()
    }

    override fun getProductsWithUnitBySearch(search: String, cartId: Long) =
        productDatabaseDao.getProductsBySearch("%$search%", cartId).flowOn(Dispatchers.IO).conflate()

    override fun getProductsWithUnitById(id: Long): Flow<ProductWithUnit> =
        productDatabaseDao.getProductsWithUnitById(id).flowOn(Dispatchers.IO).conflate()

    override fun getUnits() = productDatabaseDao.getUnits().flowOn(Dispatchers.IO).conflate()

    // Auxiliares
    override suspend fun saveFirstUnits() {
        productDatabaseDao.insertUnits(Datasource.units)
    }

    override suspend fun saveFirstProducts() {
        productDatabaseDao.insertProducts(Datasource.products)
    }

    override suspend fun saveUnits(units: List<Unit>) {
        productDatabaseDao.insertUnits(units)
    }

    override suspend fun saveProducts(products: List<Product>) {
        productDatabaseDao.insertProducts(products)
    }

}