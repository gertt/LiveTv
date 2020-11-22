package com.gprifti.livetv.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel : ViewModel() {

    var changeView = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                changeView.value = true
            }
        }
    }
}