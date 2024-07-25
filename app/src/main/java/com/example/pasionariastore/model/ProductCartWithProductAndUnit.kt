package com.example.pasionariastore.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProductCartWithProductAndUnit(
    @Embedded val productCart: ProductCart,
    @Relation(
        entity = Product::class,
        parentColumn = "product_id",
        entityColumn = "product_id"
    )
    val productWithUnit: ProductWithUnit
)