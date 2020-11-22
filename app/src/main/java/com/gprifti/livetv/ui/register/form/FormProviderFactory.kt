package com.gprifti.livetv.ui.register.form

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gprifti.livetv.data.repository.Repository

class FormProviderFactory(private val ctx: Context, private val repository: Repository) :
    ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FormViewModel(ctx,repository) as T
    }
}