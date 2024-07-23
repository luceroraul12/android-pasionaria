package com.example.pasionariastore.model

import androidx.compose.runtime.mutableStateListOf

data class CartUIState(
    val canSearchProducts: Boolean = false,
    val currentSearch: String = "",
    val showModalProductSearch: Boolean = false,
    val productCartList: MutableList<ProductCart> = mutableStateListOf(),
    val currentProductSearcheds: List<Product> = mutableListOf(),
    val currentProductCart: ProductCart? = null
) {

}
