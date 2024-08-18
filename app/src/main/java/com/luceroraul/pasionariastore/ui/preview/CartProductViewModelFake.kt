package com.luceroraul.pasionariastore.ui.preview

import com.luceroraul.pasionariastore.data.Datasource
import com.luceroraul.pasionariastore.model.state.CartProductUIState
import com.luceroraul.pasionariastore.viewmodel.CartProductViewModel
import kotlinx.coroutines.flow.update

class CartProductViewModelFake : CartProductViewModel(
    cartRepository = CartRepositoryFake(),
    productRepository = ProductRepositoryFake()
) {
    init {
        state.update { CartProductUIState(
//            productsFound = Datasource.productsWithUnit
        )
        }
    }
}