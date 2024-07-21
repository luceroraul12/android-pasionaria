package com.example.pasionariastore.model

data class ProductCart(
    val product: Product,
    val amount: AmountCart?
)

data class AmountCart(
    val quantity: Double,
    val totalPrice: Double
)