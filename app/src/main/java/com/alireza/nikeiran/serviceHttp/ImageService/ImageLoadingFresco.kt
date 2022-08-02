package com.alireza.nikeiran.serviceHttp.ImageService

import com.alireza.nikeiran.view.NikeImageView
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.IllegalStateException

class ImageLoadingFresco :ImageLoading{
    override fun load(image: NikeImageView, imageUrl: String) {
        if (image is SimpleDraweeView)
            image.setImageURI(imageUrl)
        else
            throw IllegalStateException("Imageview must is simpleDraweeView")
    }
}