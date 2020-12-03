package com.gprifti.livetv.data.repository

import com.gprifti.livetv.data.api.RetrofitInstance
import com.gprifti.livetv.data.db.FavoriteEntity
import com.gprifti.livetv.data.db.LiveTvDatabase
import com.gprifti.livetv.data.pref.PrefStorage
import org.json.JSONObject

class Repository(private val db: LiveTvDatabase, private val pref: PrefStorage) {

    /** get data from sharepreference */
    fun savEmail(email: String) = pref.saveEmail(email)

    fun getEmail() = pref.getEmail()

    /** get datas from newtwork call */
    suspend fun startServer() = RetrofitInstance.api.startServer()

    suspend fun register(payload: JSONObject) = RetrofitInstance.api.registerUser(payload)

    suspend fun getStreamsByTittle(tittle: String) = RetrofitInstance.api.geStreamsByTittle(tittle)

    suspend fun getStreamsByTittleCategory(tittle: String, category: String) = RetrofitInstance.api.getStreamsByTittleCategory(tittle, category)

    /** get and insert datas with local db */
    suspend fun readFavorite() = db.getTaskDao().readFavorite()

  //  suspend fun insertFavorite(favorite: FavoriteEntity) = db.getTaskDao().insert(favorite)

   // suspend fun deleteById(id: Long) = db.getTaskDao().deleteById(id)
}