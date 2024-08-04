package com.example.pasionariastore.ui.preview

import com.example.pasionariastore.model.state.CartProductUIState
import com.example.pasionariastore.viewmodel.CartProductViewModel
import kotlinx.coroutines.flow.update

class CartProductViewModelFake : CartProductViewModel(
    cartRepository = CartRepositoryFake(),
    productRepository = ProductRepositoryFake()
) {
    init {
        state.update { CartProductUIState(

        ) }
    }
}