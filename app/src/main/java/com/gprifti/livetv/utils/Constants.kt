package com.gprifti.livetv.utils

import androidx.lifecycle.MutableLiveData


val EMAIL_PATERN = Regex("""\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6}""")

        const val KEY_EMAIL = "email"

        const  val IMAGE_URL = "imgUrl"

        const  val VIDEO_URL = "videoUrl"

   fun <T> MutableLiveData<T>.forceRefresh() {
        this.value = this.value
}