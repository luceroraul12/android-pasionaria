package com.example.pasionariastore.model

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val imageVector: ImageVector,
    val name: String,
    val onNavigatePath: String,
    val enable: Boolean = true
)
