package com.gprifti.livetv.ui.menu.popular

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gprifti.livetv.data.model.response.StreamsModel
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.utils.InternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*

@RequiresApi(Build.VERSION_CODES.M)
class PopularViewModel @ViewModelInject constructor (@ApplicationContext private val ctx: Context, private val repository: Repository) : ViewModel() {

    var searchResult = MutableLiveData<ArrayList<StreamsModel>>()
    var stateView = MutableLiveData<Int>()

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null

    init {
        if (InternetConnection.isOnline(ctx)) {
            searchJob?.cancel()
            stateView.value = 1
            searchJob = coroutineScope.launch {
                try {
                    searchResult.value = repository.getStreamsByTittle("")
                    stateView.value = 4
                } catch (e: Exception) {
                    stateView.value = 3
                }
            }
        } else stateView.value = 2
    }

    fun onQueryTextChange(s: CharSequence) {
        if (InternetConnection.isOnline(ctx)) {
            searchJob?.cancel()
            stateView.value = 1
            searchJob = coroutineScope.launch {
                delay(2000)
                if (s.isNotEmpty())
                    searchResult.value = repository.getStreamsByTittle(s.toString())
                stateView.value = 4
            }
        } else stateView.value = 2
    }
}