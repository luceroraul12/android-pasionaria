package com.example.pasionariastore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

data class Cart(
    val id: Long = 0,
    val usernameCustomer: String = "CLIENTE",
    val usernameSeller: String = "JUAN",
    val status: String = "PENDING",
    val dateCreated: Date = Date(),
    val totalPrice: Double = 0.0
)