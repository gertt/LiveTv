package com.gprifti.livetv.data.repository

import com.gprifti.livetv.data.api.APISearch
import com.gprifti.livetv.data.pref.PrefStorage
import org.json.JSONObject
import javax.inject.Inject

class Repository @Inject constructor (private val pref: PrefStorage,private val retrofit: APISearch) {

    /** get data from sharepreference */
    fun savEmail(email: String) = pref.saveEmail(email)

    fun getEmail() = pref.getEmail()

    /** get datas from newtwork call */
    suspend fun startServer() = retrofit.startServer()

    suspend fun register(payload: JSONObject) = retrofit.registerUser(payload)

    suspend fun getStreamsByTittle(tittle: String) = retrofit.geStreamsByTittle(tittle)

    suspend fun getStreamsByTittleCategory(tittle: String, category: String) = retrofit.getStreamsByTittleCategory(tittle, category)

    /** get and insert datas with local db */
  //  suspend fun readFavorite() = db.getTaskDao().readFavorite()

  //  suspend fun insertFavorite(favorite: FavoriteEntity) = db.getTaskDao().insert(favorite)

   // suspend fun deleteById(id: Long) = db.getTaskDao().deleteById(id)
}