package com.alireza.nikeiran.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.KEY_EXTRA_DATA
import com.alireza.nikeiran.comman.NikeFragment
import com.alireza.nikeiran.comman.convertToPixel
import com.alireza.nikeiran.data.source.*
import com.alireza.nikeiran.feature.comman.ProductListAdapter
import com.alireza.nikeiran.feature.comman.VIEW_TYPE_ITEM_HORIZONTAL
import com.alireza.nikeiran.feature.list.ProductListActivity
import com.alireza.nikeiran.feature.main.BannerSliderAdapter
import com.alireza.nikeiran.feature.main.product.ProductDetailActivity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : NikeFragment(), ProductListAdapter.ProductEventListener {
    val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeViewModel.productListLatest.observe(viewLifecycleOwner){
            val lastestProductsRv=view.findViewById<RecyclerView>(R.id.lastestProductRvMain)
            val adapter: ProductListAdapter by inject{ parametersOf(VIEW_TYPE_ITEM_HORIZONTAL)}
            adapter.productEventListener=this
            lastestProductsRv.adapter=adapter
            adapter.products= it as ArrayList<Product>
            lastestProductsRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
        homeViewModel.productListPopular.observe(viewLifecycleOwner){
            val popularProductsRv=view.findViewById<RecyclerView>(R.id.popularProductRvMain)
            val adapter: ProductListAdapter by inject{ parametersOf(VIEW_TYPE_ITEM_HORIZONTAL)}
            adapter.productEventListener=this
            popularProductsRv.adapter=adapter
            adapter.products=it as ArrayList<Product>
            popularProductsRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        }

        homeViewModel.productListExpensive.observe(viewLifecycleOwner){
            val expensiveProductsRv=view.findViewById<RecyclerView>(R.id.expensiveProductRvMain)
            val adapter: ProductListAdapter by inject{ parametersOf(VIEW_TYPE_ITEM_HORIZONTAL)}
            adapter.productEventListener=this
            expensiveProductsRv.adapter=adapter
            adapter.products= it as ArrayList<Product>
            expensiveProductsRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        }

        homeViewModel.productListPoor.observe(viewLifecycleOwner){
            val poorProductsRv=view.findViewById<RecyclerView>(R.id.poorProductRvMain)
            val adapter: ProductListAdapter by inject{ parametersOf(VIEW_TYPE_ITEM_HORIZONTAL)}
            adapter.productEventListener=this
            poorProductsRv.adapter=adapter
            adapter.products= it as ArrayList<Product>
            poorProductsRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        }


        homeViewModel.progressIndicatorLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        homeViewModel.bannerList.observe(viewLifecycleOwner){
            val bannerSlider=view.findViewById<ViewPager2>(R.id.bannerSliderViewPager)
            val adapter= BannerSliderAdapter(this,it)
            bannerSlider.adapter=adapter
            val layoutParams=bannerSlider.layoutParams
            val heightV2=((bannerSlider.measuredWidth - convertToPixel(32f,requireContext())) * 173 ) /328
            layoutParams.height=heightV2.toInt()
            val indicatorDots=view.findViewById<DotsIndicator>(R.id.dots_indicator)
            indicatorDots.setViewPager2(bannerSlider)
            bannerSlider.layoutParams=layoutParams
            Timer().schedule(object :TimerTask(){
                override fun run() {
                    if (bannerSlider.currentItem < adapter.itemCount -1){
                        bannerSlider.setCurrentItem(bannerSlider.currentItem +1 ,true)
                    }else
                        bannerSlider.setCurrentItem(0,true)
                }

            },3000,3000)
        }

        val btnLatestProduct=view.findViewById<Button>(R.id.viewLatestProductBtn)
        btnLatestProduct.setOnClickListener{
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                putExtra(KEY_EXTRA_DATA, SORT_LATEST)
            })
        }
        val btnPopularProduct=view.findViewById<Button>(R.id.viewPopularProductBtn)
        btnPopularProduct.setOnClickListener{
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                putExtra(KEY_EXTRA_DATA, SORT_POPULAR)
            })
        }
        val btnPoorProduct=view.findViewById<Button>(R.id.viewPoorProductBtn)
        btnPoorProduct.setOnClickListener{
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                putExtra(KEY_EXTRA_DATA, SORT_PRICE_ASC)
            })
        }
        val btnExpensiveProduct=view.findViewById<Button>(R.id.viewExpensiveProductBtn)
        btnExpensiveProduct.setOnClickListener{
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                putExtra(KEY_EXTRA_DATA, SORT_PRICE_DESC)
            })
        }

    }

    fun layoutParamState(){
        val bannerSlider=view?.findViewById<ViewPager2>(R.id.bannerSliderViewPager)
        val layoutParams= bannerSlider!!.layoutParams
        val heightV2=((bannerSlider.measuredWidth - convertToPixel(32f,requireContext())) * 173 ) /328
        layoutParams.height=heightV2.toInt()
        bannerSlider.layoutParams=layoutParams
    }

    override fun onResume() {
        super.onResume()
        layoutParamState()
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply {
            putExtra(KEY_EXTRA_DATA,product)
        })
    }

    override fun onFavoriteBtnClick(product: Product) {
        homeViewModel.addProductToFavorite(product)
    }
}
