package com.gprifti.livetv.ui.register.form


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprifti.livetv.data.db.FavoriteEntity
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.utils.Constants
import com.gprifti.livetv.utils.InternetConnection
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class FormViewModel(
    private val ctx: Context,
    private val repository: Repository
) : ViewModel() {

    var validateForm = MutableLiveData<Boolean>()
    var backButton = MutableLiveData<Boolean>()
    var preSetEmail = MutableLiveData<String>()
    var stateView = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            preSetEmail.value = repository.getEmail()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun clickNextButtonForm(
        username: String, email: String, pass: String, surname: String, phone: String
    ) {
     //  if (username.isEmpty() || email.isEmpty() || pass.isEmpty() && surname.isEmpty() || phone.matches(
     //           Constants.PHONE_NUMBER
     //       )
     //   )
        validateForm.value = false
        //  else {
        if (InternetConnection.isOnline(ctx)) {
            viewModelScope.launch {
                stateView.value = 1
                try {
                    var result = repository.register(
                        createPayload(username, email, pass, surname, phone)
                    ).code()
                    if (result == 201) stateView.value = 4
                    else stateView.value = 3
                } catch (e: Exception) {
                    stateView.value = 3
                }
            }
        } else stateView.value = 2
        validateForm.value = true
     //     }
    }

    private fun createPayload(
        username: String, email: String, pass: String, surname: String, phone: String
    ): JSONObject {

        val payloadObj = JSONObject()
        try {
            payloadObj.put("username", username)
            payloadObj.put("email", email)
            payloadObj.put("pass", pass)
            payloadObj.put("surname", surname)
            payloadObj.put("phone", phone)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return payloadObj
    }

    fun backButton(stateBackBt: Boolean) {
          backButton.value = stateBackBt
//        viewModelScope.launch {
//            var sg = FavoriteEntity("","")
//            sg.tittle = "Discovery"
//            sg.imagePath = "https://yt3.ggpht.com/a/AATXAJxyGHIdj9W_xT_copwZI0USeFUpJcxwIdIR_t28=s900-c-k-c0x00ffffff-no-rj"
//            repository.insertFavorite(sg)
//        }
    }
}