package com.example.pasionariastore.model

data class CartUIState(
    val canSearchProducts: Boolean = false,
    val currentSearch: String = "",
    val showModalProductSearch: Boolean = false
)
