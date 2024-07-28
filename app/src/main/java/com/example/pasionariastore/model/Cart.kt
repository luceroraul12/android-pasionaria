package com.example.pasionariastore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_id")
    val id: Long = 0,
    @ColumnInfo(name = "username_customer")
    val usernameCustomer: String = "CLIENTE",
    @ColumnInfo(name = "username_seller")
    val usernameSeller: String = "JUAN",
    @ColumnInfo(name = "status")
    val status: String = "PENDING",
    @ColumnInfo(name = "date_created")
    val dateCreated: Date = Date(),
    @ColumnInfo(name = "total_price")
    val totalPrice: Double = 0.0
)

fun Date.format(): String {
    val df: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
    return df.format(this)
}