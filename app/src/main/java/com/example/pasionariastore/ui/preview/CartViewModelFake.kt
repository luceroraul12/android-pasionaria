package com.example.pasionariastore.ui.preview

import android.content.Context
import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.state.CartUIState
import com.example.pasionariastore.viewmodel.CartViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CartViewModelFake(context: Context) : CartViewModel(cartRepository = CartRepositoryFake(), context = context) {
    init {
        state.update { CartUIState(cartWithData = MutableStateFlow(Datasource.cartWithData[0])) }
    }
}