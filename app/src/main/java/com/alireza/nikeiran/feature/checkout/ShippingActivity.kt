package com.alireza.nikeiran.feature.checkout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.*
import com.alireza.nikeiran.data.source.cart.PurchaseDetail
import com.alireza.nikeiran.data.source.order.SubmitOrderResponse
import com.alireza.nikeiran.view.scrool.NikeToolbar
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.IllegalStateException

class ShippingActivity : NikeActivity() {
    val viewModel:OrderViewModel by viewModel()
    val compositeDisposable= CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)
        val toolbar=findViewById<NikeToolbar>(R.id.toolbarCheckout)
        toolbar.onBackClicked = View.OnClickListener {
            finish()
        }

        /**
         * purchase detail for totalprice,payableprice,shippingcost from cart activity
         */

        val purchaseDetail = intent.getParcelableExtra<PurchaseDetail>(KEY_EXTRA_DATA) ?: throw IllegalStateException("purchase detail cannnot be a null")
        val totalPrice=findViewById<TextView>(R.id.totalPriceCheckOut)
        val payablePrice=findViewById<TextView>(R.id.payablePriceCheckOut)
        val shippingCost=findViewById<TextView>(R.id.shipingCostCheckOut)
            totalPrice.text = formatPrice(purchaseDetail!!.total_price.toInt())
            payablePrice.text = formatPrice(purchaseDetail.payable_price.toInt())
            shippingCost.text = formatPrice(purchaseDetail.shipping_cost.toInt())
        val firstName=findViewById<EditText>(R.id.firstNameEt)
        val lastName=findViewById<EditText>(R.id.lastNameEt)
        val postalCode=findViewById<EditText>(R.id.postalCodeEt)
        val phoneNumber=findViewById<EditText>(R.id.phoneNumberEt)
        val address=findViewById<EditText>(R.id.addressEt)
        val payOnline = findViewById<Button>(R.id.payOnlineBtn)
        val payCod = findViewById<Button>(R.id.payCodBtn)

        val onClickListener = View.OnClickListener {
            if (firstName.length() > 0 && lastName.length() > 0 && postalCode.length() > 0 && phoneNumber.length() > 0 && address.length() > 0){
                viewModel.submitOrder(firstName.text.toString(),lastName.text.toString(),postalCode.text.toString(),phoneNumber.text.toString()
                    ,address.text.toString(), if (it.id == R.id.payOnlineBtn) ORDER_PAYMENT_METHOD_ONLINE else ORDER_PAYMENT_METHOD_COD)
                    .async().subscribe(object :NikeSingleObserver<SubmitOrderResponse>(compositeDisposable){
                        override fun onSuccess(t: SubmitOrderResponse) {
                            if (t.bank_gateway_url.isNotEmpty()){
                                openUrlInCustomTab(this@ShippingActivity,t.bank_gateway_url)
                            } else {
                                startActivity(Intent(this@ShippingActivity,CheckOutActivity::class.java).apply {
                                    putExtra(KEY_EXTRA_ID,t.order_id)
                                })
                            }

                        }
                    })
                finish()
            }

        }
        payOnline.setOnClickListener(onClickListener)
        payCod.setOnClickListener(onClickListener)


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}