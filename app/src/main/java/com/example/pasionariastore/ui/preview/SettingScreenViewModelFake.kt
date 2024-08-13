package com.example.pasionariastore.ui.preview

import android.content.Context
import com.example.pasionariastore.data.CustomDataStore
import com.example.pasionariastore.viewmodel.SettingViewModel

class SettingScreenViewModelFake(context: Context) : SettingViewModel(
    CustomDataStore(context)
) {
}