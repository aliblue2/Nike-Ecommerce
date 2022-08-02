package com.alireza.nikeiran.feature.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.convertToPixel
import com.alireza.nikeiran.comman.formatPrice
import com.alireza.nikeiran.data.source.Product
import com.alireza.nikeiran.data.source.orderHistory.OrderHistoryItem
import com.alireza.nikeiran.view.NikeImageView

class OrderHistoryItemAdapter(val context: Context, val orderHistoryItems: List<OrderHistoryItem> ,val  eventListener: OrderHistoryEventListener) :
    RecyclerView.Adapter<OrderHistoryItemAdapter.ViewHolder>() {
    val layoutParams: LinearLayout.LayoutParams

    init {
        val size = convertToPixel(100f, context).toInt()
        val margin = convertToPixel(8f, context).toInt()
        layoutParams = LinearLayout.LayoutParams(size, size)
        layoutParams.setMargins(margin, 0, margin, 0)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(orderHistoryItem: OrderHistoryItem) {
            val id = itemView.findViewById<TextView>(R.id.orderItemId)
            val payable = itemView.findViewById<TextView>(R.id.orderItemPrice)
            val orderProductsLl = itemView.findViewById<LinearLayout>(R.id.orderProductsLl)
            id.text = orderHistoryItem.id.toString()
            payable.text = formatPrice(orderHistoryItem.payable)
            orderProductsLl.removeAllViews()
            orderHistoryItem.order_items.forEach {
                val imageView = NikeImageView(context)
                imageView.layoutParams = layoutParams
                imageView.setImageURI(it.product.image)
                imageView.setOnClickListener { image ->
                    eventListener.onImageClick(it.product)
                }

                orderProductsLl.addView(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.order_history_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(orderHistoryItems[position])

    override fun getItemCount(): Int = orderHistoryItems.size
}

interface OrderHistoryEventListener {
    fun onImageClick(product: Product)
}