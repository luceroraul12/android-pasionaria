package com.luceroraul.pasionariastore.usecase

import com.luceroraul.pasionariastore.model.BackendLookup
import com.luceroraul.pasionariastore.model.BackendProduct
import com.luceroraul.pasionariastore.model.CartWithData
import com.luceroraul.pasionariastore.model.Product
import com.luceroraul.pasionariastore.model.ProductCart
import com.luceroraul.pasionariastore.model.Unit
import com.luceroraul.pasionariastore.model.getCartProductWithTotalPriceUpdate
import com.luceroraul.pasionariastore.model.state.CartStatus
import com.luceroraul.pasionariastore.repository.BackendRepository
import com.luceroraul.pasionariastore.repository.CartRepository
import com.luceroraul.pasionariastore.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) {

    suspend fun syncSystem() {
        val backendProducts = backendRepository.getCustomerProducts()

        if (!backendProducts.isNullOrEmpty()) {
            backendProducts.let {
                val backendUnits = it.map(BackendProduct::unitType).distinct()
                syncUnits(backendUnits = backendUnits)
                syncProducts(it)
                syncCartProducts()
            }


        }

    }

    private suspend fun syncCartProducts() {
        val pendingCarts: List<CartWithData> =
            cartRepository.getCartsWithStatus(listOf(CartStatus.PENDING.name)).first()
        val cartProductsToUpdate: MutableList<ProductCart> = mutableListOf()
        pendingCarts.forEach { cartWithData ->
            cartWithData.productCartWithData.forEach { cartProductWithData ->
                cartProductsToUpdate.add(
                    cartProductWithData.getCartProductWithTotalPriceUpdate()
                )
            }
        }

        if (cartProductsToUpdate.isNotEmpty()) {
            cartRepository.updateProductCarts(cartProductsToUpdate)
        }
    }

    private suspend fun syncProducts(backendProducts: List<BackendProduct>) {
        val products = backendProducts.map {
            Product(
                name = it.name,
                productId = it.id,
                description = it.description ?: "SIN DESCRIPCION",
                unitId = it.unitType.id,
                priceList = it.price.toDouble(),
                lastUpdate = it.lastUpdate
            )
        }
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