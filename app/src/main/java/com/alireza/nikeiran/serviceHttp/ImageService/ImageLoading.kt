package com.alireza.nikeiran.serviceHttp.ImageService

import com.alireza.nikeiran.data.source.banner.Banner
import com.alireza.nikeiran.view.NikeImageView

interface ImageLoading {

    fun load(image : NikeImageView,imageUrl:String)
}