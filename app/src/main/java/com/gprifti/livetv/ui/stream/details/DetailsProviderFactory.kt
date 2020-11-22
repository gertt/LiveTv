package com.gprifti.livetv.ui.stream.details

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gprifti.livetv.data.repository.Repository




class DetailsProviderFactory(private val ctx: Context, private val repository: Repository) :
    ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(ctx, repository) as T
    }
}