package com.example.pasionariastore.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.lifecycle.ViewModel
import com.example.pasionariastore.model.MenuItem
import com.example.pasionariastore.model.state.ResumeUIState
import com.example.pasionariastore.navigation.MyScreens
import com.example.pasionariastore.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    var state: MutableStateFlow<ResumeUIState> = MutableStateFlow(ResumeUIState())
        private set

    val menuItems: List<MenuItem> = listOf(
        MenuItem(
            name = "Pedidos",
            imageVector = Icons.Default.ShoppingCart,
            onNavigatePath = MyScreens.CartList.name
        ),
        MenuItem(
            name = "Clientes",
            imageVector = Icons.Default.Person,
            onNavigatePath = MyScreens.CartList.name,
            enable = false
        )
    )

    fun initScreen() {
        val date = LocalDate.now()
        val monthName = date.month.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
        val label = "$monthName - ${date.year}".uppercase()
        state.update { ResumeUIState(label = label) }
    }

}