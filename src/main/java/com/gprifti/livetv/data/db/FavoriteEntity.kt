package com.gprifti.livetv.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["image_path", "url_stream","live_tittle"], unique = true)])
data class FavoriteEntity(

    @ColumnInfo(name = "image_path")  var imagePath: String?,

    @ColumnInfo(name = "url_stream")  var urlStream: String?,

    @ColumnInfo(name = "live_tittle")  var tittle: String?) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

