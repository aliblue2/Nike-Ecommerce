package com.alireza.nikeiran.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.KEY_EXTRA_DATA
import com.alireza.nikeiran.data.source.banner.Banner
import com.alireza.nikeiran.serviceHttp.ImageService.ImageLoading
import com.alireza.nikeiran.view.NikeImageView
import org.koin.android.ext.android.inject
import java.lang.IllegalStateException

class BannerFragment:Fragment() {
    val imageLoading: ImageLoading by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imageView =
            layoutInflater.inflate(R.layout.banner_slide_layout, container, false) as NikeImageView
        val banner=requireArguments().getParcelable<Banner>(KEY_EXTRA_DATA)?:throw IllegalStateException("banner cannot be a null")
        imageLoading.load(imageView,banner.image)
        return imageView
    }

    companion object
    fun newInstance(banner: Banner): BannerFragment {
        return BannerFragment().apply {
            arguments=Bundle().apply {
                putParcelable(KEY_EXTRA_DATA,banner)
            }
            }
        }
}