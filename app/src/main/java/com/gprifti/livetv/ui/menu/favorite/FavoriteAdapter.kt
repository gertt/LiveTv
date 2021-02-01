package com.gprifti.livetv.ui.menu.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gprifti.livetv.R
import com.gprifti.livetv.data.db.FavoriteEntity
import com.gprifti.livetv.utils.ParseImage

class FavoriteAdapter(private val list: List<FavoriteEntity>) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: FavoriteEntity = list[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size
}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.adapter_list_favorite, parent, false)) {

    private var mTitleView: TextView = itemView.findViewById(R.id.txt_tittle)
    private var img: ImageView = itemView.findViewById(R.id.img_channel)

    fun bind(favoriteEntity: FavoriteEntity) {
        mTitleView.text = favoriteEntity.tittle
        ParseImage.parseImg(itemView.context, favoriteEntity.imagePath, img)
    }
}

