package com.example.pasionariastore.model

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.Unit

data class CartUIState(
    val canSearchProducts: Boolean = false,
    val currentSearch: String = "",
    val showModalProductSearch: Boolean = false,
    val productCartList: MutableList<ProductCartWithData> = mutableStateListOf(),
    val currentProductSearcheds: List<ProductWithUnit> = mutableListOf(),
    val currentProductCart: ProductCartWithData? = null,
    val lastSearch: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
) {

}
