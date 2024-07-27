package com.example.pasionariastore.viewmodel

import com.example.pasionariastore.repository.CartRepository
import com.example.pasionariastore.repository.CartRepositoryFake
import com.example.pasionariastore.repository.ProductRepository
import com.example.pasionariastore.repository.ProductRepositoryFake
import org.junit.Assert.assertEquals
import org.junit.Test

class CartViewModelTest {
    val cartRepository: CartRepository = CartRepositoryFake()
    val productRepository: ProductRepository = ProductRepositoryFake()
    val viewModel: CartViewModel =
        CartViewModel(cartRepository = cartRepository, productRepository = productRepository)

    @Test
    fun formatPriceSucces() {
        val result = viewModel.formatPriceNumber(77.666)

        assertEquals("ARS 77.66", result)
    }
}