package com.gprifti.livetv.ui.menu.popular

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gprifti.livetv.data.repository.Repository


class PopularProviderFactory(private val ctx: Context, private val repository: Repository) :
    ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PopularViewModel(ctx,repository) as T
    }
}
