package com.example.quickbuyapp.ui.productDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.get
import com.andremion.counterfab.CounterFab
import com.bumptech.glide.Glide
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.example.quickbuyapp.R
import com.example.quickbuyapp.model.ProductModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductDetailFragment : Fragment() {

    private lateinit var viewModel: ProductDetailViewModel

    private var img_product:ImageView ?= null
    private var btnCart:CounterFab?=null
    private var btnRating:FloatingActionButton?=null
    private var product_name:TextView?=null
    private var product_description:TextView?=null
    private var product_price:TextView?=null
    private var number_button:ElegantNumberButton?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
        val root = inflater.inflate(R.layout.product_detail_fragment, container, false)
        initView(root)

        viewModel.getMutableLiveDataProduct().observe(viewLifecycleOwner, Observer {
            displayInfo(it)
        })
        return root
    }

    private fun displayInfo(it: ProductModel?) {
        Glide.with(requireContext()).load(it!!.image).into(img_product!!)
        product_name!!.text = StringBuilder(it!!.name)
        product_description!!.text = StringBuilder(it!!.description)
        product_price!!.text = StringBuilder(it!!.price)
    }

    private fun initView(root: View?) {
        btnCart = root!!.findViewById(R.id.btnCart) as CounterFab
        img_product = root!!.findViewById(R.id.img_product) as ImageView
        btnRating = root!!.findViewById(R.id.btn_rating) as FloatingActionButton
        product_name = root!!.findViewById(R.id.product_name) as TextView
        product_description = root!!.findViewById(R.id.product_description) as TextView
        product_price = root!!.findViewById(R.id.product_price) as TextView
        number_button = root!!.findViewById(R.id.number_button) as ElegantNumberButton
    }

}