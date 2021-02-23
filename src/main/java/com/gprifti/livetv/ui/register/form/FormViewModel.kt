package com.gprifti.livetv.ui.register.form

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprifti.livetv.data.model.dto.FormStateDto
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.utils.EMAIL_PATTERN
import com.gprifti.livetv.utils.FieldType
import com.gprifti.livetv.utils.InternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class FormViewModel @ViewModelInject constructor(@ApplicationContext private val ctx: Context,private val repository: Repository) : ViewModel() {

    var validateForm = MutableLiveData<FormStateDto>()
    var backButton = MutableLiveData<Boolean>()
    var preSetEmail = MutableLiveData<String>()
    var stateView = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            preSetEmail.value = repository.getEmail()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun clickNextButtonForm(username: String, email: String, pass: String, surname: String, phone: String) {
        if (username.isEmpty() || username.length < 5)
            validateForm.value = FormStateDto(0, FieldType.USERNAME.field)
        else if (email.isEmpty() || !email.matches(EMAIL_PATTERN))
            validateForm.value = FormStateDto(1, FieldType.EMAIL.field)
        else if (pass.isEmpty() || pass.length < 5)
            validateForm.value = FormStateDto(2, FieldType.PASS.field)
        else if (surname.isEmpty() || surname.length < 5)
            validateForm.value = FormStateDto(3, FieldType.SURNAME.field)
        else if (phone.isEmpty() || phone.length < 5)
            validateForm.value = FormStateDto(4, FieldType.PHONE.field)
        else {
            if (InternetConnection.isOnline(ctx)) {
                viewModelScope.launch {
                    stateView.value = 1
                    try {
                        val result = repository.register(createPayload(username, email, pass, surname, phone)).code()
                        if (result == 201) stateView.value = 4
                        else stateView.value = 3
                    } catch (e: Exception) {
                        stateView.value = 3
                    }
                }
            } else stateView.value = 2
        }
    }

    private fun createPayload(username: String, email: String, pass: String, surname: String, phone: String): JSONObject {
        val payloadObj = JSONObject()
        try {
            payloadObj.put(FieldType.USERNAME.field, username)
            payloadObj.put(FieldType.EMAIL.field, email)
            payloadObj.put(FieldType.PASS.field, pass)
            payloadObj.put(FieldType.SURNAME.field, surname)
            payloadObj.put(FieldType.PHONE.field, phone)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return payloadObj
    }

    fun backButton(stateBackBt: Boolean) {
        backButton.value = stateBackBt
    }
}