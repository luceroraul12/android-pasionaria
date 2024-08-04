package com.example.pasionariastore.model

data class BackendProduct(
    val id: Long,
    val stock: Boolean,
    val onlyUnit: Boolean,
    val name: String,
    val description: String? = null,
    val price: Long,
    val category: BackendLookup,
    val unitType: BackendLookup
)

data class BackendLookup(
    val id: Long,
    val code: String,
    val description: String,
    val value: String? = null
)
