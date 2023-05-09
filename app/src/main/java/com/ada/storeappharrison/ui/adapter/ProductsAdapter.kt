package com.ada.storeappharrison.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ada.storeappharrison.R
import com.ada.storeappharrison.network.dto.product.ProductDto
import com.bumptech.glide.Glide

class ProductsAdapter (private var dataSet: List<ProductDto>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val description: TextView
        val price: TextView
        val poster: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            name = view.findViewById(R.id.productNameR)
            description = view.findViewById(R.id.productDescriptionR)
            price = view.findViewById(R.id.productPriceR)
            poster = view.findViewById(R.id.productPosterR)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.product_row, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = dataSet[position]
        holder.name.text = product.name
        holder.description.text = product.description
        holder.price.text = "$ ${product.price.toString()}"
        Glide.with(holder.itemView.context).load(product.imageUrl)
            .into(holder.poster)
    }

    fun update(dataSet: List<ProductDto>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

}