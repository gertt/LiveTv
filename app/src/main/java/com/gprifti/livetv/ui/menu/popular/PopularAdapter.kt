package com.gprifti.livetv.ui.menu.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gprifti.livetv.R
import com.gprifti.livetv.data.model.response.StreamsModel
import com.gprifti.livetv.utils.ParseImage

class PopularAdapter(private val list: List<StreamsModel>) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: StreamsModel = list[position]
        holder.bind(movie)
    }
    override fun getItemCount(): Int = list.size
}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.adapter_list_popular, parent, false)) {
    private var mTitleView: TextView? = null
    private var img: ImageView

    init {
        mTitleView = itemView.findViewById(R.id.txtTaskName)
        img = itemView.findViewById(R.id.img_id)
    }

    fun bind(movie: StreamsModel) {
        mTitleView?.text = movie.tittle
        ParseImage.parseImg(itemView.context,movie.img,img)
    }
}