package com.example.pasionariastore.model

data class BackendLogin(
    val username: String,
    val password: String
)

data class BackendLoginResponse(
    val jwt: String
)
