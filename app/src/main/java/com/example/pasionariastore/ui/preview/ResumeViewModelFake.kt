package com.example.pasionariastore.ui.preview

import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.viewmodel.ResumeViewModel
import kotlinx.coroutines.flow.update

class ResumeViewModelFake : ResumeViewModel(CartRepositoryFake()) {

    init {
        state.update {
            it.copy(
                label = "MES - 2024",
                cartsWithData = Datasource.cartWithData.toMutableList()
            )
        }
    }
}