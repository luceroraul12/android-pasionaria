package com.luceroraul.pasionariastore.ui.preview

import android.content.Context
import com.luceroraul.pasionariastore.model.state.LoginUIState
import com.luceroraul.pasionariastore.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.update

class LoginViewModelFake constructor(context: Context) : LoginViewModel(
    backendRepository = BackendRepositoryFake(),
    customDataStore = CustomDataStoreFake(context = context)
) {
    init {
        state.update { LoginUIState() }
    }
}