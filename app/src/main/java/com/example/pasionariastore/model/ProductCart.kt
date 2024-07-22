package com.example.pasionariastore.model

data class ProductCart(
    val product: Product,
    val amount: AmountCart = AmountCart()
)

data class AmountCart(
    val quantity: Double = 0.0,
    val totalPrice: Double = 0.0
)