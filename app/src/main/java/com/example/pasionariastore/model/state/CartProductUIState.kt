package com.example.pasionariastore.model.state

import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductWithUnit
import kotlinx.coroutines.flow.MutableSharedFlow

data class CartProductUIState(
    val currentSearch: String = "",
    val productsFound: List<ProductWithUnit> = emptyList(),
    val currentProductCart: ProductCart = ProductCart(),
    val currentProductWithUnit: ProductWithUnit = ProductWithUnit(),
    val canSearchProducts: Boolean = true,
    val showModalProductSearch: Boolean = false,
    val canUpdateQuantity: Boolean = false,
    val lastSearch: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>(),
    val initCartId: Long = 0,
    val isNew: Boolean = false
)
