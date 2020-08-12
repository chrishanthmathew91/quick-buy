package com.example.quickbuyapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.quickbuyapp.R
import com.example.quickbuyapp.model.ItemToDisplay
import kotlinx.android.synthetic.main.layout_dashboard.view.*

class ItemToDisplayAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var items : List<ItemToDisplay> = ArrayList()

    // creating different view holders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_dashboard , parent , false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            // this is what I want to display
            is BlogViewHolder -> {
                // referencing view holder and calling bind written below
                // bind the data to the particular view holder that is currently in the view
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // submit the list of data using this function to the recycler view
    fun submitList(ItemToDisplay : List<ItemToDisplay>){
        items = ItemToDisplay
    }

    // we are extending using RecyclerView.ViewHolder because this is the class we used above.
    class BlogViewHolder constructor(
        itemView : View
    ): RecyclerView.ViewHolder(itemView){

        // id in layout_blog_list_item
        val ItemImage1: ImageView = itemView.item1
        val ItemImage2: ImageView = itemView.item2
        val ItemImage3: ImageView = itemView.item3
        val ItemImage4: ImageView = itemView.item4
        val item_title: TextView = itemView.blog_title
        //val blog_author:TextView = itemView.blog_author


        fun bind(ItemToDisplay : ItemToDisplay){
            item_title.setText(ItemToDisplay.title)

            // to set image
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            // loads the image on given link in app
            // into loads that image into blog_image the image view which we have
            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(ItemToDisplay.image1)
                .into(ItemImage1)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(ItemToDisplay.image2)
                .into(ItemImage2)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(ItemToDisplay.image3)
                .into(ItemImage3)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(ItemToDisplay.image4)
                .into(ItemImage4)
        }

    }
}