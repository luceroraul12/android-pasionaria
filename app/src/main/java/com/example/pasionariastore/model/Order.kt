package com.example.pasionariastore.model

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Cart(
    val id: Long = 0,
    val usernameCustomer: String = "CLIENTE",
    val usernameSeller: String = "JUAN",
    val status: String = "PENDING",
    val dateCreated: Date = Date(),
    val totalPrice: Double = 0.0
)

fun Date.format(): String {
    val df: DateFormat = SimpleDateFormat("dd/MM/yyyy hh:ss")
    return df.format(this)
}