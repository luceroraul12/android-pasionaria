package com.luceroraul.pasionariastore.ui.preview

import com.luceroraul.pasionariastore.data.Datasource
import com.luceroraul.pasionariastore.model.state.CartListUIState
import com.luceroraul.pasionariastore.usecase.CartSynchronize
import com.luceroraul.pasionariastore.viewmodel.CartListViewModel
import kotlinx.coroutines.flow.update

class CartListViewModelFake : CartListViewModel(
    cartRepository = CartRepositoryFake(),
    cartSynchronize = CartSynchronize(
        backendRepository = BackendRepositoryFake(),
        cartRepository = CartRepositoryFake()
    )
) {
    init {
        state.update { CartListUIState(cartsWithData = Datasource.cartWithData.toMutableList()) }
    }
}