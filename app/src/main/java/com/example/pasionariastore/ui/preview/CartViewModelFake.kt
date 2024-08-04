package com.example.pasionariastore.ui.preview

import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.state.CartUIState
import com.example.pasionariastore.viewmodel.CartViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CartViewModelFake : CartViewModel(cartRepository = CartRepositoryFake()) {
    init {
        state.update { CartUIState(cartWithData = MutableStateFlow(Datasource.cartWithData[0])) }
    }
}