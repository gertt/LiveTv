package com.gprifti.livetv.ui.menu.search


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprifti.livetv.data.model.response.StreamsModel
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.utils.InternetConnection
import kotlinx.coroutines.*
import kotlin.collections.ArrayList


@RequiresApi(Build.VERSION_CODES.M)
class SearchViewModel(private val ctx: Context, private val repository: Repository) : ViewModel() {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null

    var searchResult = MutableLiveData<ArrayList<StreamsModel>>()
    var filterCategory = MutableLiveData<Int>()
    var keyword: String = ""
    var stateView = MutableLiveData<Int>()


    enum class FilterType(val filter: String) {
        ALL("all"), INTERNACIONAL("internacional"), NATIONAL("national")
    }

    init {
        if (InternetConnection.isOnline(ctx)) {
            stateView.value = 1
            coroutineScope.launch {
                try {
                    searchResult.value = repository.getStreamsByTittle("")
                    stateView.value = 4
                } catch (e: Exception) {
                    stateView.value = 3
                }
            }
        } else stateView.value = 2
    }

    fun onQueryTextChange(s: CharSequence, start: Int, before: Int, count: Int) {
        if (InternetConnection.isOnline(ctx)) {
            searchJob?.cancel()
            stateView.value = 1
            searchJob =  coroutineScope.launch {
                    s?.let {
                        delay(400)
                        searchResult.value = repository.getStreamsByTittle(s.toString())
                        keyword = s.toString()
                        stateView.value = 4
                    }
            }
        } else stateView.value = 2
    }

    fun changeFilterState(category: Int) {
        when (category) {
            1 -> {
                callService(keyword, FilterType.ALL.filter)
                filterCategory.value = 1
            }
            2 -> {
                callService(keyword, FilterType.INTERNACIONAL.filter)
                filterCategory.value = 2
            }
            3 -> {
                callService(keyword, FilterType.NATIONAL.filter)
                filterCategory.value = 3
            }
        }
    }

    private fun callService(keyword: String, category: String) {
        if (InternetConnection.isOnline(ctx)) {
            viewModelScope.launch {
                stateView.value = 1
                if(category.equals(FilterType.ALL.filter)){
                    searchResult.value = repository.getStreamsByTittle(keyword)
                } else{
                    searchResult.value = repository.getStreamsByTittleCategory(keyword, category)
                }
                stateView.value = 4
            }
        } else stateView.value = 2
    }
}
