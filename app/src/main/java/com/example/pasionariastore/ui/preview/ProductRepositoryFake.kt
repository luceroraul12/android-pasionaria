package com.example.pasionariastore.ui.preview

import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductWithUnit
import com.example.pasionariastore.model.Unit
import com.example.pasionariastore.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryFake : ProductRepository {
    override fun getProductsWithUnit(): Flow<List<ProductWithUnit>> =
        flow { Datasource.productsWithUnit }

    override fun getProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getProductsWithUnitBySearch(search: String): Flow<List<ProductWithUnit>> =
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

}