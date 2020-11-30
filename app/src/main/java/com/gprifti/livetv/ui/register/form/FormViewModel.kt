package com.gprifti.livetv.ui.register.form


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprifti.livetv.data.model.dto.FormStateDto
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.utils.EMAIL_PATERN
import com.gprifti.livetv.utils.FileldType
import com.gprifti.livetv.utils.InternetConnection
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class FormViewModel(private val ctx: Context, private val repository: Repository) : ViewModel() {

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
            validateForm.value = FormStateDto(0, FileldType.USERNAME.field)
        else if (email.isEmpty() || !email.matches(EMAIL_PATERN))
            validateForm.value = FormStateDto(1, FileldType.EMAIL.field)
        else if (pass.isEmpty() || pass.length < 5)
            validateForm.value = FormStateDto(2, FileldType.PASS.field)
        else if (surname.isEmpty() || surname.length < 5)
            validateForm.value = FormStateDto(3, FileldType.SURNAME.field)
        else if (phone.isEmpty() || phone.length < 5)
            validateForm.value = FormStateDto(4, FileldType.PHONE.field)
        else {

            if (InternetConnection.isOnline(ctx)) {
                viewModelScope.launch {
                    stateView.value = 1
                    try {
                        var result = repository.register(createPayload(username, email, pass, surname, phone)).code()
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
            payloadObj.put(FileldType.USERNAME.field, username)
            payloadObj.put(FileldType.EMAIL.field, email)
            payloadObj.put(FileldType.PASS.field, pass)
            payloadObj.put(FileldType.SURNAME.field, surname)
            payloadObj.put(FileldType.PHONE.field, phone)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return payloadObj
    }

    fun backButton(stateBackBt: Boolean) {
        backButton.value = stateBackBt
    }
}