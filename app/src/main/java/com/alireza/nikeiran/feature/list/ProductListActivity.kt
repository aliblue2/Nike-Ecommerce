package com.alireza.nikeiran.feature.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.KEY_EXTRA_DATA
import com.alireza.nikeiran.comman.NikeActivity
import com.alireza.nikeiran.data.source.Product
import com.alireza.nikeiran.feature.comman.ProductListAdapter
import com.alireza.nikeiran.feature.comman.VIEW_TYPE_ITEM_GRID
import com.alireza.nikeiran.feature.comman.VIEW_TYPE_ITEM_VERTICAL
import com.alireza.nikeiran.feature.main.product.ProductDetailActivity
import com.alireza.nikeiran.view.scrool.NikeToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListActivity : NikeActivity(), ProductListAdapter.ProductEventListener {
    val viewModel:ProductListViewModel by inject { parametersOf(intent.extras!!.getInt(
        KEY_EXTRA_DATA)) }
    override fun onCreate(savedInstanceState: Bundle?) {

        val adapter:ProductListAdapter by inject { parametersOf(VIEW_TYPE_ITEM_VERTICAL) }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val gridLayoutManager=GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false)


        viewModel.productLiveData.observe(this){
            val rvListProduct=findViewById<RecyclerView>(R.id.rvProductList)

            rvListProduct.adapter=adapter
            adapter.products = it as ArrayList<Product>
            rvListProduct.layoutManager=gridLayoutManager
            adapter.productEventListener=this

        }

        viewModel.progressIndicatorLiveData.observe(this){
            setProgressIndicator(it)
        }

        viewModel.sortLiveData.observe(this){
            val titleSortTv=findViewById<TextView>(R.id.tvTitleFilter)
            titleSortTv.text = getString(it)
        }
        val toolbarProducts=findViewById<NikeToolbar>(R.id.toolbarListProduct)
        toolbarProducts.onBackClicked= View.OnClickListener {
            finish()
        }

        val btnChangeViewType=findViewById<ImageView>(R.id.btnChangerViewType)
        btnChangeViewType.setOnClickListener {
            if (adapter.viewType == VIEW_TYPE_ITEM_VERTICAL) {
                btnChangeViewType.setImageResource(R.drawable.ic_round_list_24)
                adapter.viewType = VIEW_TYPE_ITEM_GRID
                gridLayoutManager.spanCount=2
                adapter.notifyDataSetChanged()
            }else{
                btnChangeViewType.setImageResource(R.drawable.ic_grid)
                adapter.viewType= VIEW_TYPE_ITEM_VERTICAL
                gridLayoutManager.spanCount = 1
                adapter.notifyDataSetChanged()
            }

        }

        val btnChangeSort=findViewById<View>(R.id.btnChangeSort)

        btnChangeSort.setOnClickListener {
            val dialog=MaterialAlertDialogBuilder(this)
                .setTitle(R.string.filter)
                .setSingleChoiceItems(R.array.sortTitlesArray,viewModel.sort
                ) { display, selectedSortIndex ->
                    viewModel.changeSortByUser(selectedSortIndex)
                    adapter.notifyDataSetChanged()
                    display.dismiss()
                }
                .setPositiveButton("بستن"
                ) { display, p1 ->
                    display.dismiss()
                }
                .setBackground(getDrawable(R.drawable.background_sort))
            dialog.show()
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(KEY_EXTRA_DATA,product)
        })
    }

    override fun onFavoriteBtnClick(product: Product) {
        viewModel.onBtnFavorClick(product)
    }
}