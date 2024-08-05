package com.example.pasionariastore.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.lifecycle.ViewModel
import com.example.pasionariastore.model.MenuItem
import com.example.pasionariastore.navigation.MyScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(): ViewModel() {

    val menuItems: List<MenuItem> = listOf(
        MenuItem(name = "Pedidos", imageVector = Icons.Default.ShoppingCart, onNavigatePath = MyScreens.CartList.name),
        MenuItem(name = "Clientes", imageVector = Icons.Default.Person, onNavigatePath = MyScreens.CartList.name, enable = false)
    )

}