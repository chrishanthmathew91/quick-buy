package com.example.quickbuyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbuyapp.DataSource
import com.example.quickbuyapp.R
import com.example.quickbuyapp.ui.ItemToDisplayAdapter
import com.example.quickbuyapp.ui.TopSpacingItemDecoration

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var itemAdapter : ItemToDisplayAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView : RecyclerView = root.findViewById(R.id.recycler_view)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingDecoration = TopSpacingItemDecoration(40)
            addItemDecoration(topSpacingDecoration)
            itemAdapter = ItemToDisplayAdapter()
            adapter = itemAdapter
        }

        addDataSet()

        return root
    }

    private fun addDataSet(){
        val data = DataSource.createDataSet()
        itemAdapter.submitList(data)
    }

}