package com.example.pasionariastore.model

data class Product(
    val name: String,
    val description: String = "SIN DESCRIPCION",
    val priceList: Double,
    val unit: Unit
)

data class Unit(
    val name: String,
    val value: Double
)
