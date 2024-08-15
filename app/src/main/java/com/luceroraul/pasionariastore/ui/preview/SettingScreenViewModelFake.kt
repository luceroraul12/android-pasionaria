package com.luceroraul.pasionariastore.ui.preview

import android.content.Context
import com.luceroraul.pasionariastore.data.CustomDataStore
import com.luceroraul.pasionariastore.viewmodel.SettingViewModel

class SettingScreenViewModelFake(context: Context) : SettingViewModel(
    CustomDataStore(context)
) {
}