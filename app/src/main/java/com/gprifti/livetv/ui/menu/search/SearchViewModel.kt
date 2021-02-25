package com.gprifti.livetv.ui.menu.search

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprifti.livetv.data.db.FavoriteEntity
import com.gprifti.livetv.data.model.response.StreamsModel
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.utils.FilterType
import com.gprifti.livetv.utils.InternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*


@RequiresApi(Build.VERSION_CODES.M)
class SearchViewModel @ViewModelInject constructor (@ApplicationContext private val ctx: Context, private val repository: Repository) : ViewModel() {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null
    private var keyword: String = ""


    var searchResult = MutableLiveData<ArrayList<StreamsModel>>()
    var filterCategory = MutableLiveData<Int>()

    var arrayList = ArrayList<StreamsModel>()
    var arrayList2 = ArrayList<FavoriteEntity>()

    var stateView = MutableLiveData<Int>()

    init {
        if (InternetConnection.isOnline(ctx)) {
            stateView.value = 1
            coroutineScope.launch {
                try {
                    arrayList = repository.getStreamsByTittle("")
                    arrayList2 = repository.readFavorite() as ArrayList<FavoriteEntity>
                    arrayList.forEachIndexed { i, el1 ->
                        arrayList2.forEachIndexed { _, el2 ->

                            System.out.println("Elsrsr: ${el1.id}  and el2: ${el2.id}")
                            if (el1.id == el2.id){
                                arrayList[i].heartStatus = true
                            }
                        }
                    }
                    searchResult.postValue(arrayList)
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
                s.let {
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
                callService(keyword, FilterType.INTERNATIONAL.filter)
                filterCategory.value = 2
            }
            3 -> {
                callService(keyword, FilterType.NATIONAL.filter)
                filterCategory.value = 3
            }
        }
    }

    fun insertFavorite(streamsModel: StreamsModel) {
        coroutineScope.launch {
            try {
                with(streamsModel) {
                    var favorite = FavoriteEntity(img, urlStream, tittle)
                    repository.insertFavorite(favorite)
                }
            } catch (e: Exception) {}
        }
    }

    fun deleteFavorite(streamsModel: StreamsModel) {
        coroutineScope.launch {
            try {
                with(streamsModel) {
                    streamsModel.id?.let { repository.deleteById(it.toLong()) }
                }
            } catch (e: Exception) {}
        }
    }



    private fun callService(keyword: String, category: String) {
        if (InternetConnection.isOnline(ctx)) {
            viewModelScope.launch {
                stateView.value = 1
                if (category == FilterType.ALL.filter) {
                    searchResult.value = repository.getStreamsByTittle(keyword)
                } else {
                    searchResult.value = repository.getStreamsByTittleCategory(keyword, category)
                }
                stateView.value = 4
            }
        } else stateView.value = 2
    }



}
