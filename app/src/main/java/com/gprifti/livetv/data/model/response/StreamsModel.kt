package com.gprifti.livetv.data.model.response

import com.google.gson.annotations.SerializedName

 class StreamsModel {

    @field:SerializedName("id")
    var id: Int? = null

    @field:SerializedName("tittle")
    var tittle: String? = null

    @field:SerializedName("img")
    var img: String? = null

    @field:SerializedName("url_stream")
    var urlStream: String? = null

    @field:SerializedName("heart_status")
    var heartStatus: Boolean? = false
}