package com.luceroraul.pasionariastore.model.state

data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val enableLoginButton: Boolean = false
)
