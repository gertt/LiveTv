package com.gprifti.livetv.ui.splash

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.utils.InternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.M)
class SplashViewModel(private val ctx: Context, private val repository: Repository) : ViewModel() {

    var changeView = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            if (InternetConnection.isOnline(ctx))
                repository.startServer()
                delay(3000)
            withContext(Dispatchers.Main) {
                if (repository.getEmail()?.isNotEmpty() == true)
                    changeView.value = 0
                else
                    changeView.value = 1
            }
        }
    }
}