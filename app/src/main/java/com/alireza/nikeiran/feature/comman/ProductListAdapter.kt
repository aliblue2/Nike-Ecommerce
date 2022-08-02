package com.alireza.nikeiran.feature.comman

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.formatPrice
import com.alireza.nikeiran.data.source.Product
import com.alireza.nikeiran.serviceHttp.ImageService.ImageLoading
import com.alireza.nikeiran.view.NikeImageView
import java.lang.IllegalStateException

const val VIEW_TYPE_ITEM_HORIZONTAL=0
const val VIEW_TYPE_ITEM_VERTICAL=1
const val VIEW_TYPE_ITEM_GRID=2
class ProductListAdapter(val imageLoading: ImageLoading ,var viewType: Int= VIEW_TYPE_ITEM_HORIZONTAL) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
    var productEventListener:ProductEventListener?=null
    var products=ArrayList<Product>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val resLayout= when (viewType){
            VIEW_TYPE_ITEM_HORIZONTAL -> R.layout.products_item
            VIEW_TYPE_ITEM_VERTICAL -> R.layout.products_item_list
            VIEW_TYPE_ITEM_GRID -> R.layout.products_item_grid
            else -> throw IllegalStateException("layout res is not valid")
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(resLayout, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindProducts(products[position])
    }

    override fun getItemCount(): Int {
    return products.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProductIv=itemView.findViewById<NikeImageView>(R.id.imageProductIv)
        val titleProductTv=itemView.findViewById<TextView>(R.id.tittleProductTv)
        val previousPriceProductTv=itemView.findViewById<TextView>(R.id.previousPriceProductTv)
        val favoriteBtn=itemView.findViewById<ImageView>(R.id.favoriteButton)
        val priceProductTV=itemView.findViewById<TextView>(R.id.priceProductTv)
        fun bindProducts(product: Product) :Unit{
            imageLoading.load(imageProductIv,product.image)
            titleProductTv.text=product.title
            titleProductTv.isSelected= true

            if (product.isFavorite)
                favoriteBtn.setImageResource(R.drawable.ic_filled_favorite_24)
            else
                favoriteBtn.setImageResource(R.drawable.ic_favorite_border)

            favoriteBtn.setOnClickListener {
                productEventListener?.let {
                    productEventListener?.onFavoriteBtnClick(product)
                }
                product.isFavorite = !product.isFavorite
                notifyItemChanged(adapterPosition)

            }

            itemView.setOnClickListener {
                productEventListener.let {
                    productEventListener?.onProductClick(product)
                }
            }
            previousPriceProductTv.text= formatPrice(product.previous_price)
            previousPriceProductTv.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
            priceProductTV.text= formatPrice(product.price)

        }
    }

    interface ProductEventListener{
        fun onProductClick(product: Product)

        fun onFavoriteBtnClick(product: Product)
    }
}