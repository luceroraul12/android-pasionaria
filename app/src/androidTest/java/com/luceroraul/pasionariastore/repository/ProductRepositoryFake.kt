package com.luceroraul.pasionariastore.repository

import com.luceroraul.pasionariastore.data.Datasource
import com.luceroraul.pasionariastore.model.Product
import com.luceroraul.pasionariastore.model.ProductWithUnit
import com.luceroraul.pasionariastore.model.Unit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryFake : ProductRepository {
    override fun getProductsWithUnit(): Flow<List<ProductWithUnit>> =
        flow { Datasource.productsWithUnit }

    override fun getProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getProductsWithUnitBySearch(search: String, cartId: Long): Flow<List<ProductWithUnit>> =
        flow { Datasource.productsWithUnit.filter { p -> p.product.name.contains(search) } }

    override fun getProductsWithUnitById(id: Long): Flow<ProductWithUnit> =
        flow { Datasource.productsWithUnit.find { r -> r.product.productId == id } }

    override fun getUnits(): Flow<List<Unit>> = flow { Datasource.units }

    override suspend fun saveFirstUnits() {
        TODO("Not yet implemented")
    }

    override suspend fun saveFirstProducts() {
        TODO("Not yet implemented")
    }

    override suspend fun saveUnits(units: List<Unit>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveProducts(products: List<Product>) {
        TODO("Not yet implemented")
    }

}