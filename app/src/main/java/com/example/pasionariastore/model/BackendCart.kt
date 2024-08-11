package com.example.pasionariastore.model

import java.util.Date

data class BackendCart(
    val cartId: Long,
    val backendCartId: Long? = null,
    val dateCreated: Date,
    val products: List<BackendCartProduct>,
    val status: String? = null
)

data class BackendCartProduct(
    val cartProductId: Long,
    val backendCartProductId: Long? = null,
    val productId: Long,
    val quantity: Double,
    val price: Double
)