package com.gprifti.livetv.ui.menu.popular

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.gprifti.livetv.R
import androidx.databinding.library.baseAdapters.BR
import com.gprifti.livetv.data.model.response.StreamsModel
import com.gprifti.livetv.databinding.AdapterListPopularBinding
import com.gprifti.livetv.utils.IMAGE_URL
import com.gprifti.livetv.utils.ParseImage
import com.gprifti.livetv.utils.TITTLE_STREAM
import com.gprifti.livetv.utils.VIDEO_URL


class PopularAdapter(private val ctx: Context, private val nav: NavController, private val items: ArrayList<StreamsModel>): RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: AdapterListPopularBinding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.adapter_list_popular, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bind(items[position], nav) }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: AdapterListPopularBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StreamsModel, nav: NavController) {
            binding.txtTaskName.text = item.tittle
            ParseImage.parseImg(itemView.context, item.img, binding.imgId)

            binding.cardView.setOnClickListener {
                nav.navigate(
                    R.id.action_popularListFragment_to_detailsFragment2, bundleOf(
                        IMAGE_URL to item.img.toString(),
                        VIDEO_URL to item.urlStream,
                        TITTLE_STREAM to item.tittle


                    )
                )
            }
            binding.setVariable(BR.streamsModel, item)
            binding.executePendingBindings()
        }
    }
}
