package com.luceroraul.pasionariastore.model.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.luceroraul.pasionariastore.model.ProductCart
import com.luceroraul.pasionariastore.model.ProductWithUnit
import kotlinx.coroutines.flow.MutableSharedFlow

data class CartProductUIState(
    val currentSearch: String = "",
    val productsFound: List<ProductWithUnit> = emptyList(),
    val currentProductCart: ProductCart = ProductCart(),
    val currentProductWithUnit: ProductWithUnit = ProductWithUnit(),
    val canSearchProducts: Boolean = true,
    val canUpdateQuantity: Boolean = false,
    val lastSearch: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>(),
    val initCartId: Long = 0,
    val isNew: Boolean = false
)
