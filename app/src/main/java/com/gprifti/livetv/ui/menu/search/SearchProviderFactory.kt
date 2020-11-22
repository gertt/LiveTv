package com.gprifti.livetv.ui.menu.search

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.ui.menu.popular.PopularViewModel

class SearchProviderFactory(private  val ctx:Context,private val repository: Repository
) :
    ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(ctx,repository) as T
    }
}
