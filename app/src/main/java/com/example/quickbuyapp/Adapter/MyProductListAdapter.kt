package com.example.quickbuyapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quickbuyapp.Callback.IRecyclerItemClickListener
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.EventBus.ProductItemClick
import com.example.quickbuyapp.R
import com.example.quickbuyapp.model.ProductModel
import org.greenrobot.eventbus.EventBus

class MyProductListAdapter(internal var context: Context,
                           internal var productList: List<ProductModel>) :
    RecyclerView.Adapter<MyProductListAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyProductListAdapter.MyViewHolder {
        return  MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_product_item ,
                parent , false))
    }
    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(productList.get(position).image).into(holder.item_image!!)
        holder.text_item_name!!.setText(productList.get(position).name)
        holder.text_item_price!!.setText(productList.get(position).price)

        // Event
        holder.setListener(object:IRecyclerItemClickListener{
            override fun onItemClick(view: View, pos: Int) {
                Common.productSelected = productList.get(pos)
                EventBus.getDefault().postSticky(ProductItemClick(true , productList.get(pos)))
            }

        })
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var text_item_name : TextView? = null
        var text_item_price : TextView? = null
        var item_image : ImageView? = null
        var image_fav : ImageView? = null
        var image_cart : ImageView? = null

        internal var listener: IRecyclerItemClickListener?= null

        fun setListener(listener: IRecyclerItemClickListener){
            this.listener = listener
        }

        init {
            text_item_name = itemView.findViewById(R.id.text_item_name) as TextView
            text_item_price = itemView.findViewById(R.id.text_item_price) as TextView
            item_image = itemView.findViewById(R.id.item_image) as ImageView
            image_cart = itemView.findViewById(R.id.img_quick_cart) as ImageView
            image_fav = itemView.findViewById(R.id.img_fav) as ImageView
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            listener!!.onItemClick(view!! , adapterPosition)
        }
    }
}