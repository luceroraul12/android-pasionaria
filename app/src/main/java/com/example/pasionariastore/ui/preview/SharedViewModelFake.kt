package com.example.pasionariastore.ui.preview

import android.content.Context
import com.example.pasionariastore.interceptor.BackendInterceptor
import com.example.pasionariastore.interceptor.ErrorInterceptor
import com.example.pasionariastore.viewmodel.SharedViewModel

class SharedViewModelFake constructor(context: Context) : SharedViewModel(
    errorInterceptor = ErrorInterceptor(),
    context = context
) {

}