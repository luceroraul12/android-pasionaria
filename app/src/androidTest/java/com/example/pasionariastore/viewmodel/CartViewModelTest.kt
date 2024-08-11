package com.example.pasionariastore.viewmodel

import androidx.compose.ui.platform.LocalContext
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pasionariastore.repository.CartRepository
import com.example.pasionariastore.repository.CartRepositoryFake
import org.junit.Assert.assertEquals
import org.junit.Test

class CartViewModelTest {
    val cartRepository: CartRepository = CartRepositoryFake()
    val viewModel: CartViewModel =
        CartViewModel(
            cartRepository = cartRepository,
            context = InstrumentationRegistry.getInstrumentation().context
        )

    @Test
    fun format_text_rounded_success() {
        val result = viewModel.formatPriceNumber(77.666)

        assertEquals("ARS77.67", result)
    }

    @Test
    fun format_text_rounded_zero() {
        val result = viewModel.formatPriceNumber(0.0)

        assertEquals("ARS0.00", result)
    }
}