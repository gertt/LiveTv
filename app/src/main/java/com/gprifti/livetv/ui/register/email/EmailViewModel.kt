package com.gprifti.livetv.ui.register.email

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprifti.livetv.data.model.dto.FormStateDto
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.utils.EMAIL_PATTERN
import com.gprifti.livetv.utils.Event
import kotlinx.coroutines.launch

class EmailViewModel @ViewModelInject constructor (private val repository: Repository) : ViewModel() {

    val validateEmail: MutableLiveData<Event<FormStateDto>> = MutableLiveData()
    var setEmail = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            if (repository.getEmail()?.isNotBlank() == true)
                setEmail.value = repository.getEmail()
        }
    }

    fun clickNextButton(email: String) {
        viewModelScope.launch {
            if (email.isNotEmpty() && email.matches(EMAIL_PATTERN)) {
                email(email)
                validateEmail.value = Event(FormStateDto(0, ""))
            } else
                validateEmail.value = Event(FormStateDto(1, ""))
        }
    }

    private fun email(validateEmail: String) {
        repository.savEmail(validateEmail)
    }
}

