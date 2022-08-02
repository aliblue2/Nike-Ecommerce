package com.alireza.nikeiran.feature.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alireza.nikeiran.data.source.banner.Banner

class BannerSliderAdapter(fragment: Fragment,val banners:List<Banner>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return banners.size
    }

    override fun createFragment(position: Int): Fragment=BannerFragment().newInstance(banners[position])
}