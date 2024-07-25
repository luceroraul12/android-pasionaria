package com.example.pasionariastore.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ProductWithUnit(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "unit_id",
        associateBy = Junction(ProductUnitCrossRef::class)
    )
    val unit: Unit
)