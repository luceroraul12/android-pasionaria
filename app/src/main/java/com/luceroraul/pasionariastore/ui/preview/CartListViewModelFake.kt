package com.luceroraul.pasionariastore.ui.preview

import com.luceroraul.pasionariastore.data.Datasource
import com.luceroraul.pasionariastore.usecase.CartSynchronize
import com.luceroraul.pasionariastore.viewmodel.CartListViewModel
import kotlinx.coroutines.flow.update

class CartListViewModelFake : CartListViewModel(
    CartRepositoryFake(),
    CartSynchronize(BackendRepositoryFake(), cartRepository = CartRepositoryFake())
) {
    init {
        state.update { it.copy(cartsWithData = Datasource.cartWithData.toMutableList()) }
    }
}