package com.gprifti.livetv.data.model.response

import com.google.gson.annotations.SerializedName


 class StreamsModel {

    @field:SerializedName("id")
    var id: Int? = null

    @field:SerializedName("tittle")
    var tittle: String? = null

    @field:SerializedName("img")
    var img: String? = null

     @field:SerializedName("header")
     var header: Int? = null

    @field:SerializedName("username")
    var username: String? = null

    @field:SerializedName("url_stream")
    var urlStream: String? = null

}