package com.gprifti.livetv.data.api

import com.gprifti.livetv.data.model.response.StreamsModel
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface APISearch {

    @GET("/startServer")
    suspend fun startServer(): Any

    @Headers("Content-Type: application/json")
    @POST("/register")
    suspend fun registerUser(@Body payload: JSONObject): Response<Any>

    @GET("/streams")
    suspend fun geStreamsByTittle(@Query("tittle_like") tittle: String): ArrayList<StreamsModel>

    @GET("/streams")
    suspend fun getStreamsByTittleCategory(@Query("tittle_like") tittle: String,
                                           @Query("category") category: String): ArrayList<StreamsModel>

   /* @GET("/streams")
    suspend fun getStreamsByCategory(@Query("category") category: String): ArrayList<StreamsModel>*/
}
