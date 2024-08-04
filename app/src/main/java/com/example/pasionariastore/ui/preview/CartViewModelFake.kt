package com.example.pasionariastore.ui.preview

import com.example.pasionariastore.viewmodel.CartViewModel

class CartViewModelFake : CartViewModel(cartRepository = CartRepositoryFake()) {

}