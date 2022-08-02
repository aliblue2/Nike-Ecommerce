package com.alireza.nikeiran.feature.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.KEY_EXTRA_DATA
import com.alireza.nikeiran.comman.NikeActivity
import com.alireza.nikeiran.data.source.Product
import com.alireza.nikeiran.feature.main.product.ProductDetailActivity
import com.alireza.nikeiran.serviceHttp.ImageService.ImageLoading
import com.alireza.nikeiran.view.scrool.NikeToolbar
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteProductsActivity : NikeActivity(),FavoriteProductsAdapter.FavoriteProductsEventListener {
    val viewModel:FavoriteProductsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val favoriteProductsRv = findViewById<RecyclerView>(R.id.favoriteProductsrv)
        val toolbar = findViewById<NikeToolbar>(R.id.nikeToolbar)
        val help = findViewById<ImageView>(R.id.helpBtn)
        help.setOnClickListener {
            showSnackBar("برای حذف از لیست انگشت خود را نگه دارید")
        }
        toolbar.onBackClicked = View.OnClickListener {
            finish()
        }

        viewModel.productLive.observe(this){
            if (it.isNotEmpty()) {
                favoriteProductsRv.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                favoriteProductsRv.adapter =
                    FavoriteProductsAdapter(it as MutableList<Product>, this, get())
            }else {
                showEmptyState(R.layout.empty_state_favorite)
                val emptyTittle= findViewById<TextView>(R.id.emptyStateFavoriteTv)
                emptyTittle.text = getString(R.string.cartEmptyStateDefault)

            }
            }
    }

    override fun onItemLongClick(product: Product) {
        viewModel.remove(product)
        showSnackBar("با موفقیت حذف شد")
    }

    override fun onItemClick(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(KEY_EXTRA_DATA,product)
        })
    }
}