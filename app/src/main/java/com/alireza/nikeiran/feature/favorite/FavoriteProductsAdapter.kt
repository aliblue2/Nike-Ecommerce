package com.alireza.nikeiran.feature.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.data.source.Product
import com.alireza.nikeiran.serviceHttp.ImageService.ImageLoading
import com.alireza.nikeiran.view.NikeImageView

class FavoriteProductsAdapter(val products : MutableList<Product>,val eventListener: FavoriteProductsEventListener,val imageLoading: ImageLoading) :RecyclerView.Adapter<FavoriteProductsAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tittle= itemView.findViewById<TextView>(R.id.tittleProductFavoriteTv)
        val imageView= itemView.findViewById<NikeImageView>(R.id.imageProductFavoriteIv)
        fun bind(product: Product){
            imageLoading.load(imageView,product.image)
            tittle.text = product.title
            itemView.setOnClickListener{
                eventListener.onItemClick(product)
            }

            itemView.setOnLongClickListener {
                eventListener.onItemLongClick(product)
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                return@setOnLongClickListener false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favorite_products_items,parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }


    interface FavoriteProductsEventListener{
        fun onItemLongClick(product: Product)

        fun onItemClick(product: Product)
    }
}