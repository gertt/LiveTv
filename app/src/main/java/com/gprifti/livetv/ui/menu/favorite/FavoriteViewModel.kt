package com.gprifti.livetv.ui.menu.favorite

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprifti.livetv.data.db.FavoriteEntity
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.utils.InternetConnection
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
class FavoriteViewModel(private val ctx: Context, private val repository: Repository) :
        ViewModel() {

    var favoriteResult = MutableLiveData<List<FavoriteEntity>>()
    var stateView = MutableLiveData<Int>()

    init {
        if (InternetConnection.isOnline(ctx)) {
            viewModelScope.launch {
                stateView.value = 1
                try {
                    favoriteResult.value = repository.readFavorite()
                    stateView.value = 4
                } catch (e: Exception) {
                    stateView.value = 3
                }
            }
        } else stateView.value = 2
    }
}



