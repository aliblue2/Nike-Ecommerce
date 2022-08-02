package com.alireza.nikeiran.feature.checkout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.KEY_EXTRA_DATA
import com.alireza.nikeiran.comman.KEY_EXTRA_ID

import com.alireza.nikeiran.comman.NikeActivity
import com.alireza.nikeiran.comman.formatPrice
import com.alireza.nikeiran.feature.home.HomeFragment
import com.alireza.nikeiran.feature.main.MainActivity
import com.alireza.nikeiran.feature.order.OrderHistoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.parameter.parametersOf

class CheckOutActivity :NikeActivity() {
    val viewModel:CheckOutViewModel by viewModel {
        val uri : Uri? = intent.data
        if (uri != null)
            parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        else
            parametersOf(intent.extras!!.getInt(KEY_EXTRA_ID))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)
        val tittle = findViewById<TextView>(R.id.statusOrderTittleTv)
        val status = findViewById<TextView>(R.id.statusOrderTv)
        val price = findViewById<TextView>(R.id.orderPriceTv)
        val animate = findViewById<LottieAnimationView>(R.id.animateCheckOut)
        val orderHistoryBtn = findViewById<Button>(R.id.orderHistoryBtn)
        orderHistoryBtn.setOnClickListener {
            startActivity(Intent(this,OrderHistoryActivity::class.java))
        }
        val backHomeBtn = findViewById<Button>(R.id.backHomeBtn)
        backHomeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        viewModel.paymentLiveData.observe(this){
            if (it.purchase_success) {
                animate.setAnimation(R.raw.animate_done)
                tittle.text = getString(R.string.successPurchaseTittle)
            }else{
                animate.setAnimation(R.raw.cancell_animate)
                tittle.setTextColor(R.color.red)
                tittle.text = getString(R.string.unSuccessPurchase)
            }
            status.text = it.payment_status
            price.text = formatPrice(it.payable_price)

        }
    }
}