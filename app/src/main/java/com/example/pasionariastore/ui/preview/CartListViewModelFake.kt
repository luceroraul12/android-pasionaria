package com.example.pasionariastore.ui.preview

import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.viewmodel.CartListViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartListViewModelFake : CartListViewModel(CartRepositoryFake()) {
    init {
        state.update { it.copy(cartsWithData = Datasource.cartWithData.toMutableList()) }
    }
}