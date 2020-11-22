package com.gprifti.livetv.utils

class Constants {

    companion object {
        val EMAIL_PATERN = Regex("""\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6}""")
      //  const val BASE_URL = "base url"
        const val KEY_EMAIL = "email"
        val PHONE_NUMBER = Regex("^\\+(?:[0-9] ?){6,14}[0-9]$")

    }
}