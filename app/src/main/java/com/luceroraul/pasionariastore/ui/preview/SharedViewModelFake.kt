package com.luceroraul.pasionariastore.ui.preview

import android.content.Context
import com.luceroraul.pasionariastore.interceptor.BackendInterceptor
import com.luceroraul.pasionariastore.interceptor.ErrorInterceptor
import com.luceroraul.pasionariastore.viewmodel.SharedViewModel

class SharedViewModelFake constructor(context: Context) : SharedViewModel(
    errorInterceptor = ErrorInterceptor(),
    context = context
) {

}