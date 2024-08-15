package com.luceroraul.pasionariastore.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BackendProduct(
    val id: Long = 0,
    val stock: Boolean = true,
    val onlyUnit: Boolean = false,
    val name: String = "PRODUCTO BACKEND",
    val description: String? = null,
    val price: Int = 26587,
    val category: BackendLookup = BackendLookup(),
    val unitType: BackendLookup,
    val lastUpdate: Date? = null
)

data class BackendLookup(
    val id: Long = 0,
    val code: String = "",
    val description: String = "",
    val value: String? = null
)
