package com.alireza.nikeiran.feature.cart

import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.formatPrice
import com.alireza.nikeiran.data.source.cart.CartItem
import com.alireza.nikeiran.data.source.cart.PurchaseDetail
import com.alireza.nikeiran.serviceHttp.ImageService.ImageLoading
import com.alireza.nikeiran.view.NikeImageView
import okhttp3.internal.notifyAll
import org.w3c.dom.Text
import java.util.Collections.list

const val VIEW_TYPE_CART_ITEMS = 0
const val VIEW_TYPE_PURCHASE_DETAIL = 1
class CartItemAdapter(val imageLoading: ImageLoading ,
                      val cartItems:MutableList<CartItem> ,
                      val cartItemViewCallBacks: CartItemViewCallBacks) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var purchaseDetail:PurchaseDetail?= null
    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartImage=itemView.findViewById<NikeImageView>(R.id.cartProductIv)
        val cartTittleProduct=itemView.findViewById<TextView>(R.id.cartTittleItemTv)
        val cartPreviousPriceTv=itemView.findViewById<TextView>(R.id.cartPreviousPriceItemTv)
        val cartPriceTv=itemView.findViewById<TextView>(R.id.cartPriceItemTv)
        val cartCountTv=itemView.findViewById<TextView>(R.id.cartCountItemTv)
        val cartIncreaseIv=itemView.findViewById<ImageView>(R.id.cartIncreaseItemIv)
        val cartDecreaseIv=itemView.findViewById<ImageView>(R.id.cartDecreaseItemIv)
        val btnDeleteFromCart=itemView.findViewById<View>(R.id.deleteFromCart)
        val progressCartCount=itemView.findViewById<ProgressBar>(R.id.progressCountCart)
        fun bindCartItem(cartItem: CartItem){
            imageLoading.load(cartImage,cartItem.product.image)
            cartTittleProduct.text = cartItem.product.title
            cartPreviousPriceTv.text = formatPrice(cartItem.product.discount)
            cartPriceTv.text = formatPrice(cartItem.product.price)
            cartCountTv.text = cartItem.count.toString()

            cartIncreaseIv.setOnClickListener {
                cartItem.changeCountProgressBarIsVisible = true
                progressCartCount.visibility = View.VISIBLE
                cartCountTv.visibility = View.INVISIBLE
                cartItemViewCallBacks.onIncreaseCartItem(cartItem)
            }

            progressCartCount.visibility = if (cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.GONE
            cartCountTv.visibility = if (cartItem.changeCountProgressBarIsVisible) View.INVISIBLE else View.VISIBLE

            cartDecreaseIv.setOnClickListener {
                if (cartItem.count>1){
                    cartItem.changeCountProgressBarIsVisible =true
                    progressCartCount.visibility = View.VISIBLE
                    cartCountTv.visibility = View.INVISIBLE
                    cartItemViewCallBacks.onDecreaseCartItem(cartItem)
                }
            }
            btnDeleteFromCart.setOnClickListener {
                cartItemViewCallBacks.onRemoveCartItemClick(cartItem)
            }
            cartImage.setOnClickListener {
                cartItemViewCallBacks.onImageCartItemClick(cartItem)
            }
        }
    }

    class PurchaseDetailViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val payablePriceTv=itemView.findViewById<TextView>(R.id.payablePricePurchaseDetail)
        val totalPriceTv=itemView.findViewById<TextView>(R.id.totalPricePurchaseDetail)
        val shippingCostPriceTv=itemView.findViewById<TextView>(R.id.shipingCostPurchaseDetail)
        fun bindPurchase( payablePrice:String , totalPrice:String , shippingCost:String){
            payablePriceTv.text = formatPrice(payablePrice.toInt())
            totalPriceTv.text= formatPrice(totalPrice.toInt())
            shippingCostPriceTv.text = formatPrice(shippingCost.toInt())

        }
    }



    override fun getItemViewType(position: Int): Int {
        if (position == cartItems.size)
            return VIEW_TYPE_PURCHASE_DETAIL
        else
            return VIEW_TYPE_CART_ITEMS
    }

    interface CartItemViewCallBacks{
        fun onRemoveCartItemClick(cartItem: CartItem)

        fun onDecreaseCartItem(cartItem: CartItem)

        fun onIncreaseCartItem(cartItem: CartItem)

        fun onImageCartItemClick(cartItem: CartItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_CART_ITEMS)
            return CartItemViewHolder(LayoutInflater.from(parent.context).
            inflate(R.layout.cart_rv_items,parent,false))
        else
            return PurchaseDetailViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.purchase_detail_items,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if (holder is CartItemViewHolder)
           holder.bindCartItem(cartItems[position])
        else if (holder is PurchaseDetailViewHolder)
            purchaseDetail?.let {
                holder.bindPurchase(it.payable_price,it.total_price,it.shipping_cost)
            }
    }

    override fun getItemCount(): Int = cartItems.size + 1


    fun removeCartItem(cartItem: CartItem){
        val index=cartItems.indexOf(cartItem)
        if (index>-1){
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun increaseCartItem(cartItem: CartItem){
        val index = cartItems.indexOf(cartItem)
        if (index > -1){

            cartItems[index].changeCountProgressBarIsVisible =false
            notifyItemChanged(index)
        }
    }


    fun decreaseCartItem(cartItem: CartItem){
        val index = cartItems.indexOf(cartItem)
        if (index > -1){

            cartItems[index].changeCountProgressBarIsVisible =false
            notifyItemChanged(index)
        }
    }

}
