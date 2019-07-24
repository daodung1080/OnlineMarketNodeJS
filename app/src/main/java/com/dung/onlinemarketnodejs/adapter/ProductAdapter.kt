package com.dung.onlinemarketnodejs.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dung.onlinemarketnodejs.user.MainScreenActivity
import com.dung.onlinemarketnodejs.R
import com.dung.onlinemarketnodejs.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_product.view.*
import java.text.DecimalFormat

class ProductAdapter(var context: Context, var list: ArrayList<Product>, var activity: MainScreenActivity) :
    RecyclerView.Adapter<ProductAdapter.ProductHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.list_item_product, p0, false)
        return ProductHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductHolder, p1: Int) {
        var product = list.get(p1)
        Picasso.get().load(product.image).into(holder.imgProduct)
        holder.tvName.text = product.name
        var fm = DecimalFormat("###,###,###")
        holder.tvPrice.text = "${fm.format(product.price * 23000)} VND"
        holder.cvProduct.setOnClickListener {
            activity.productClicked(p1)
        }
    }

    class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgProduct = view.imgProduct
        var cvProduct = view.cvProduct
        var tvName = view.tvName
        var tvPrice = view.tvPrice
    }
}