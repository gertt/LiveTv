package com.gprifti.livetv.ui.menu.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.gprifti.livetv.R
import com.gprifti.livetv.data.db.FavoriteEntity
import com.gprifti.livetv.databinding.AdapterListFavoriteBinding
import com.gprifti.livetv.utils.IMAGE_URL
import com.gprifti.livetv.utils.ParseImage
import com.gprifti.livetv.utils.VIDEO_URL

class FavoriteAdapter(private val ctx: Context, private val nav: NavController, private val items: ArrayList<FavoriteEntity>): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: AdapterListFavoriteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.adapter_list_favorite, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bind(items[position], nav) }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: AdapterListFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteEntity, nav: NavController) {
            binding.txtTittle.text = item.tittle
            ParseImage.parseImg(itemView.context, item.imagePath, binding.imgChannel)

            binding.cardView.setOnClickListener {
                nav.navigate(
                    R.id.action_favoriteFragment_to_detailsFragment2, bundleOf(
                        IMAGE_URL to item.imagePath.toString(),
                        VIDEO_URL to item.urlStream
                    )
                )
            }
            binding.setVariable(BR.streamsModel, item)
            binding.executePendingBindings()
        }
    }
}


