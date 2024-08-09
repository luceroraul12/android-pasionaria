package com.example.pasionariastore.ui.preview

import android.content.Context
import com.example.pasionariastore.model.state.LoginUIState
import com.example.pasionariastore.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.update

class LoginViewModelFake constructor(context: Context) : LoginViewModel(
    backendRepository = BackendRepositoryFake(),
    customDataStore = CustomDataStoreFake(context = context)
) {
    init {
        state.update { LoginUIState() }
    }
}