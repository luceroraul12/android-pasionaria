package com.example.pasionariastore.ui.preview

import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.viewmodel.CartListViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartListViewModelFake : CartListViewModel(CartRepositoryFake()) {
    init {
        viewModelScope.launch {
            cartRepository.getCartsWithStatus(emptyList()).collect { data ->
                state.update {
                    it.copy(
                        cartsWithData = data.toMutableList()
                    )
                }
            }
        }


    }
}