package com.gprifti.livetv.ui.register.email

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprifti.livetv.data.model.dto.FormStateDto
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.utils.Constants.Companion.EMAIL_PATERN
import com.gprifti.livetv.utils.FileldType
import kotlinx.coroutines.launch

class EmailViewModel(private val repository: Repository) : ViewModel() {

    var validateEmail = MutableLiveData<FormStateDto>()
    var setEmail = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            if (repository.getEmail()?.isNotBlank() == true)
                setEmail.value = repository.getEmail()
        }
    }

    fun clickNextButton(email: String) {
        viewModelScope.launch {
            if (email.isNotEmpty() && email.matches(EMAIL_PATERN)) {
                email(email)
                validateEmail.value = FormStateDto(0, "")
            } else
                validateEmail.value = FormStateDto(1, FileldType.EMAIL.field)
        }
    }

    private suspend fun email(validateEmail: String) {
        repository.savEmail(validateEmail)
    }
}

