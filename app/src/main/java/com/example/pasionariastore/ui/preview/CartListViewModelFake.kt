package com.example.pasionariastore.ui.preview

import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.usecase.CartSynchronize
import com.example.pasionariastore.viewmodel.CartListViewModel
import kotlinx.coroutines.flow.update

class CartListViewModelFake : CartListViewModel(
    CartRepositoryFake(),
    CartSynchronize(BackendRepositoryFake(), cartRepository = CartRepositoryFake())
) {
    init {
        state.update { it.copy(cartsWithData = Datasource.cartWithData.toMutableList()) }
    }
}