package com.example.quickbuyapp.ui.productlist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbuyapp.Adapter.MyProductListAdapter
import com.example.quickbuyapp.MyPopularCategoriesAdapter
import com.example.quickbuyapp.R
import com.example.quickbuyapp.ui.home.HomeViewModel

class ProductListFragment : Fragment() {

    private lateinit var viewModel: ProductListViewModel

    var recycler_product_list : RecyclerView ?= null
    var layoutAnimationController : LayoutAnimationController ?= null

    var adapter : MyProductListAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        val root = inflater.inflate(R.layout.product_list_fragment, container, false)

        initViews(root)

        viewModel.getMutableProductModelListData().observe(viewLifecycleOwner , Observer {
            adapter = MyProductListAdapter(requireContext(),it)
            recycler_product_list!!.adapter = adapter
            recycler_product_list!!.layoutAnimation = layoutAnimationController
        })

        return root
    }

    private fun initViews(root: View?) {
        recycler_product_list = root!!.findViewById(R.id.recycler_product_list) as RecyclerView
        recycler_product_list!!.setHasFixedSize(true)
        recycler_product_list!!.layoutManager = LinearLayoutManager(context)

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_item_from_left)
    }
}