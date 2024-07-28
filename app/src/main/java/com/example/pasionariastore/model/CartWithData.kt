package com.example.pasionariastore.model

import androidx.room.Embedded
import androidx.room.Relation

data class CartWithData(
    @Embedded val cart: Cart,
    @Relation(
        entity = ProductCart::class,
        parentColumn = "cart_id",
        entityColumn = "cart_id"
    )
    val productCartWithData: List<ProductCartWithData>
)