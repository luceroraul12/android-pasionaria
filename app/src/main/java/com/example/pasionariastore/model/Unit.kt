package com.example.pasionariastore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Unit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "unit_id")
    val unitId: Long = 0,
    @ColumnInfo(name = "name_unit")
    val name: String,
    @ColumnInfo(name = "name_type")
    val nameType: String = "Gramos",
    @ColumnInfo(name = "value")
    val value: Double = 0.0
)