package com.example.pasionariastore.usecase

import com.example.pasionariastore.model.BackendLookup
import com.example.pasionariastore.model.BackendProduct
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.Unit
import com.example.pasionariastore.repository.BackendRepository
import com.example.pasionariastore.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

enum class UnitTypes(val value: String) {
    GR(value = "Gramos"),
    KG(value = "Kilogramos"),
    ML(value = "Miligramos"),
    U(value = "Unidades"),
}

@Singleton
class ProductSynchronizer @Inject constructor(
    private val backendRepository: BackendRepository,
    private val productRepository: ProductRepository
) {

    suspend fun syncSystem() {
        val backendProducts = backendRepository.getCustomerProducts()

        if (!backendProducts.isNullOrEmpty()) {
            backendProducts.let {
                val backendUnits  = it.map(BackendProduct::unitType).distinct()
                syncUnits(backendUnits = backendUnits)
                syncProducts(it)
            }

        }

    }

    private suspend fun syncProducts(backendProducts: List<BackendProduct>) {
        val products = backendProducts.map { Product(
            name = it.name,
            productId = it.id,
            description = it.description ?: "SIN DESCRIPCION",
            unitId = it.unitType.id,
            priceList = it.price.toDouble()
        ) }
        productRepository.saveProducts(products)
    }

    private suspend fun syncUnits(backendUnits: List<BackendLookup>) {
        val units = backendUnits.map {
            Unit(
                unitId = it.id,
                name = it.description,
                value = it.value?.toDoubleOrNull() ?: 0.0,
                nameType = getNameType(it)
            )
        }
        productRepository.saveUnits(units)
    }

    fun getNameType(backendUnit: BackendLookup): String {
        return UnitTypes.entries.firstOrNull {
            backendUnit.description.contains(
                it.name,
                ignoreCase = true
            )
        }?.value
            ?: "Gramos"
    }

}