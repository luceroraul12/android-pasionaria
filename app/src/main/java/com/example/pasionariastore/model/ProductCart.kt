package com.example.pasionariastore.model

data class ProductCart(
    val product: Product,
    val amount: AmountCart? = null
)

data class AmountCart(
    val quantity: Double,
    val totalPrice: Double
)