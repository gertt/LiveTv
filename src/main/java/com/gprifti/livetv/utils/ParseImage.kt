package com.gprifti.livetv.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gprifti.livetv.R

class ParseImage {

    companion object {

        fun parseImg(ctx: Context, imageSource: String?, imagePlace: ImageView) {
            Glide.with(ctx)
                    .applyDefaultRequestOptions(
                            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.ic_launcher_background)
                    )
                    .load(imageSource)
                    .into(imagePlace)
        }
    }
}