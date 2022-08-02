package com.alireza.nikeiran.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.KEY_EXTRA_DATA
import com.alireza.nikeiran.comman.NikeCompletableObserver
import com.alireza.nikeiran.comman.NikeFragment
import com.alireza.nikeiran.data.source.SORT_POPULAR
import com.alireza.nikeiran.data.source.cart.CartItem
import com.alireza.nikeiran.feature.Auth.AuthActivity
import com.alireza.nikeiran.feature.checkout.ShippingActivity
import com.alireza.nikeiran.feature.list.ProductListActivity
import com.alireza.nikeiran.feature.main.product.ProductDetailActivity
import com.alireza.nikeiran.serviceHttp.ImageService.ImageLoading
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject


class CartFragment : NikeFragment(), CartItemAdapter.CartItemViewCallBacks {
    val cartViewModel:CartViewModel by viewModel()
    var adapter:CartItemAdapter?=null
    val imageLoading:ImageLoading by inject()
    val compositeDisposable= CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvCartItemAdapter=view.findViewById<RecyclerView>(R.id.cartItemrv)
        val btnPayCheckOut=view.findViewById<Button>(R.id.payCheckOutBtn)
        btnPayCheckOut.setOnClickListener {
            val intent = Intent(requireContext(),ShippingActivity::class.java).apply {
                putExtra(KEY_EXTRA_DATA,cartViewModel.purchaseDetailLiveData.value)
            }
            startActivity(intent)
        }
        cartViewModel.cartItemLiveData.observe(viewLifecycleOwner){
            rvCartItemAdapter.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            adapter = CartItemAdapter(imageLoading,it as MutableList<CartItem>,this)
            rvCartItemAdapter.adapter =adapter
        }


        cartViewModel.purchaseDetailLiveData.observe(viewLifecycleOwner){
            adapter?.let { adapter ->
                adapter.purchaseDetail = it

            }
        }
        cartViewModel.progressIndicatorLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        cartViewModel.emptyStateLiveData.observe(viewLifecycleOwner){ livedata ->
            val emptyIdLayout = view.findViewById<View>(R.id.emptyStateLayout)
            if (livedata.mustShow){
                val emptyState = showEmptyState(R.layout.empty_state_layout)
                emptyState?.let {
                    val emptyStateTv= view.findViewById<TextView>(R.id.emptyStateMessage)
                    val emptyStateCtaBtn= view.findViewById<Button>(R.id.emptyStateCtaBtn)
                    val emptyStateCtaBtnAllProducts= view.findViewById<Button>(R.id.emptyStateCtaBtnProducts)
                    emptyStateTv.text = getString(livedata.messageResId)
                    emptyStateCtaBtn.visibility = if (livedata.mustShowCallToAction) View.VISIBLE else View.GONE
                    emptyStateCtaBtn.setOnClickListener {
                        startActivity(Intent(requireContext(),AuthActivity::class.java))
                    }
                    emptyStateCtaBtnAllProducts.visibility = if (livedata.mustShowCtaAllProducts) View.VISIBLE else View.GONE
                    emptyStateCtaBtnAllProducts.setOnClickListener {
                        startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                            putExtra(KEY_EXTRA_DATA , SORT_POPULAR)
                        })
                    }

                }
            }
            else
                emptyIdLayout?.visibility = View.GONE
        }

    }



    override fun onRemoveCartItemClick(cartItem: CartItem) {
        cartViewModel.removeFromCart(cartItem).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    adapter!!.removeCartItem(cartItem)
                    showSnackBar(getString(R.string.SuccessRemoveCart))
                }

            })
    }

    override fun onDecreaseCartItem(cartItem: CartItem) {
        cartViewModel.decreaseCartItemCount(cartItem).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    adapter!!.decreaseCartItem(cartItem)
                }

            })
    }

    override fun onIncreaseCartItem(cartItem: CartItem)
    {
        cartViewModel.increaseCartItemCount(cartItem).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    adapter!!.increaseCartItem(cartItem)
                }

            })
    }

    override fun onImageCartItemClick(cartItem: CartItem) {
        val intent=Intent(requireContext(),ProductDetailActivity::class.java).apply {
            putExtra(KEY_EXTRA_DATA,cartItem.product)
        }
        startActivity(intent)
    }
    override fun onStart() {
        super.onStart()
        cartViewModel.refresh()
    }
    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}