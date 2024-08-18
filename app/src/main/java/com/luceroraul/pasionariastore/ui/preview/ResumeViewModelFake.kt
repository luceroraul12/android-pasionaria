package com.luceroraul.pasionariastore.ui.preview

import com.luceroraul.pasionariastore.data.Datasource
import com.luceroraul.pasionariastore.viewmodel.ResumeViewModel
import kotlinx.coroutines.flow.update

class ResumeViewModelFake : ResumeViewModel(CartRepositoryFake()) {

    init {
        state.update {
            it.copy(
                label = "Mes - 2024",
                cartsWithData = Datasource.cartWithData.toMutableList(),
                topProducts = Datasource.products.toMutableList()
            )
        }
    }
}