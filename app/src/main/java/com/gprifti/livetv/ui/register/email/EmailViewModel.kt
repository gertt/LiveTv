package com.gprifti.livetv.ui.register.email

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.utils.Constants.Companion.EMAIL_PATERN
import kotlinx.coroutines.launch

class EmailViewModel(
    private val repository: Repository
) : ViewModel() {

    var validateEmail = MutableLiveData<Boolean>()
    var setEmail = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            if(repository.getEmail()?.isNotBlank() == true)
                setEmail.value = repository.getEmail()
        }
    }

    fun clickNextButton(email: String) {
        viewModelScope.launch {
            if (email.isNotEmpty() && email.matches(EMAIL_PATERN)) {
                email(email)
                validateEmail.value = true
            } else
                validateEmail.value = false
        }
    }

    private suspend fun email(validateEmail: String) {
        repository.savEmail(validateEmail)
    }
}

