package com.alireza.nikeiran.feature.order

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.KEY_EXTRA_DATA
import com.alireza.nikeiran.comman.NikeActivity
import com.alireza.nikeiran.data.source.Product
import com.alireza.nikeiran.feature.main.product.ProductDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderHistoryActivity : NikeActivity() , OrderHistoryEventListener{
    val viewModel:OrderHistoryViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_histort)
        val orderHistoryRv = findViewById<RecyclerView>(R.id.orderHistoryRv)
        viewModel.orderItems.observe(this){
            if (it.isEmpty()){
                showEmptyState(R.layout.empty_state_order_history)
                val tittle = findViewById<TextView>(R.id.emptyStateOrderHistoryTittle)
                tittle.text = getString(R.string.orderHistoryEmpty)
            }
            else {
                orderHistoryRv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL , false)
                val adapter = OrderHistoryItemAdapter(applicationContext,it,this)
                orderHistoryRv.adapter = adapter
            }

        }
        viewModel.progressIndicatorLiveData.observe(this){
            setProgressIndicator(it)
        }
    }

    override fun onImageClick(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(KEY_EXTRA_DATA,product)
        })
    }
}