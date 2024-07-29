package com.example.pasionariastore.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ProductWithUnit(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "unit_id",
        entityColumn = "unit_id"
    )
    val unit: Unit
)