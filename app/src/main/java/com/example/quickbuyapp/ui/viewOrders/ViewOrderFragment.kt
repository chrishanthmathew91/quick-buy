package com.example.quickbuyapp.ui.viewOrders

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbuyapp.Adapter.MyOrderAdapter
import com.example.quickbuyapp.Callback.ILoadOrderCallbackListener
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.R
import com.example.quickbuyapp.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dmax.dialog.SpotsDialog
import java.util.*
import kotlin.collections.ArrayList

class ViewOrderFragment : Fragment() , ILoadOrderCallbackListener {

    companion object {
        fun newInstance() = ViewOrderFragment()
    }

    private lateinit var viewOrderModel: ViewOrderViewModel
    private lateinit var dialog:AlertDialog
    private lateinit var recycler_order: RecyclerView
    private lateinit var listener: ILoadOrderCallbackListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOrderModel = ViewModelProviders.of(this).get(ViewOrderViewModel::class.java)
        val root = inflater.inflate(R.layout.view_order_fragment, container, false)
        initViews(root)
        loadOrderFromFirebase()

        viewOrderModel!!.mutableLiveDataOrderList.observe(viewLifecycleOwner , Observer {
            Collections.reverse(it!!)
            val adapter = MyOrderAdapter(requireContext() , it!!)
            recycler_order!!.adapter = adapter
        })

        return root
    }

    private fun loadOrderFromFirebase() {
        dialog.show()
        val orderList = ArrayList<Order>()
        FirebaseDatabase.getInstance().getReference(Common.ORDER_REF)
            .orderByChild("userId")
            .equalTo(FirebaseAuth.getInstance().currentUser!!.uid)
            .limitToLast(100)
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(orderSnapshot in snapshot.children){
                        val order = orderSnapshot.getValue(Order::class.java)
                        order!!.orderNumber = orderSnapshot.key
                    }
                    listener.onLoadOrderSuccess(orderList)
                }

                override fun onCancelled(error: DatabaseError) {
                    listener.onLoadOrderFailure(error.message)
                }

            })
    }

    private fun initViews(root: View?) {

        listener = this

        dialog = SpotsDialog.Builder().setContext(requireContext()).setCancelable(false).build()

        recycler_order = root!!.findViewById(R.id.recycler_order) as RecyclerView
        recycler_order.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireContext())
        recycler_order.layoutManager = layoutManager
        recycler_order.addItemDecoration(DividerItemDecoration(requireContext() , layoutManager.orientation))
    }


    override fun onLoadOrderSuccess(orderList: List<Order>) {
        dialog.dismiss()
        viewOrderModel!!.setMutableLiveDataOrderList(orderList)
    }

    override fun onLoadOrderFailure(message: String) {
        dialog.dismiss()
        Toast.makeText(requireContext() , message , Toast.LENGTH_SHORT).show()
    }

}