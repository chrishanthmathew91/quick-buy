package com.example.quickbuyapp.ui.cart

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.example.quickbuyapp.Adapter.MyCartAdapter
import com.example.quickbuyapp.Callback.IMyButtonCallback
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.Common.MySwipeHelper
import com.example.quickbuyapp.Database.CartDataSource
import com.example.quickbuyapp.Database.CartDatabase
import com.example.quickbuyapp.Database.LocalCartDataSource
import com.example.quickbuyapp.EventBus.CountCartEvent
import com.example.quickbuyapp.EventBus.HideFABCart
import com.example.quickbuyapp.EventBus.UpdateItemInCart
import com.example.quickbuyapp.R
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cart.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.StringBuilder

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var cartviewModel: CartViewModel
    private var cartDataSource:CartDataSource?=null
    private var compositeDisposable:CompositeDisposable= CompositeDisposable()
    private var recyclerViewState:Parcelable?=null

    var txt_empty_cart:TextView?=null
    var txt_total_price:TextView?=null
    var recycler_cart:RecyclerView?=null
    var group_place_holder:CardView?=null
    var adapter:MyCartAdapter?=null

    override fun onResume() {
        super.onResume()
        calculateTotalPrice()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        EventBus.getDefault().postSticky(HideFABCart(true))

        cartviewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        cartviewModel.initCartDataSource(requireContext())
         val root= inflater.inflate(R.layout.fragment_cart, container, false)
        initViews(root)
        cartviewModel.getMutableLiveDataCartItem().observe(viewLifecycleOwner, Observer {
            if(it == null || it.isEmpty() ){
                recycler_cart!!.visibility=View.GONE
                group_place_holder!!.visibility=View.GONE
                txt_empty_cart!!.visibility=View.VISIBLE
            }
            else{
                recycler_cart!!.visibility=View.VISIBLE
                group_place_holder!!.visibility=View.VISIBLE
                txt_empty_cart!!.visibility=View.GONE

                adapter= MyCartAdapter(requireContext() ,it)
                recycler_cart!!.adapter= adapter
            }
        })
        return root
    }

    private fun initViews(root:View) {

        setHasOptionsMenu(true)
        cartDataSource=LocalCartDataSource(CartDatabase.getInstance(context).cartDAO())
        recycler_cart=root.findViewById(R.id.recycler_cart)
        recycler_cart!!.setHasFixedSize(true)
        val layoutManager=LinearLayoutManager(context)
        recycler_cart!!.layoutManager=layoutManager
        recycler_cart!!.addItemDecoration(DividerItemDecoration(context,layoutManager.orientation))

        val swipe= object :MySwipeHelper(requireContext(),recycler_cart!!,200)
        {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(MyButton(context!!,
                "Delete",
                30,
                0,
                Color.parseColor("#FF3C30"),
                    object :IMyButtonCallback{
                        override fun onClick(pos: Int) {
                            val deleteItem= adapter!!.getItemAtPosition(pos)
                            cartDataSource!!.deleteCart(deleteItem)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object: SingleObserver<Int>{
                                    override fun onSubscribe(d: Disposable) {

                                    }

                                    override fun onSuccess(t: Int) {
                                        adapter!!.notifyItemRemoved(pos)
                                        sumCart()
                                        EventBus.getDefault().postSticky(CountCartEvent(true))
                                        Toast.makeText(context,"Delete Item Successful",Toast.LENGTH_LONG).show()
                                    }
                                    override fun onError(e: Throwable) {
                                        Toast.makeText(context,""+e.message,Toast.LENGTH_LONG).show()
                                    }
                                })
                        }
                    }))
            }

        }


        txt_empty_cart=root.findViewById(R.id.txt_empty_cart)
        txt_total_price=root.findViewById(R.id.txt_total_price)
        group_place_holder=root.findViewById(R.id.group_place_holder)

    }

    private fun sumCart() {
        cartDataSource!!.sumPrice(FirebaseAuth.getInstance().currentUser!!.uid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<Double> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(t:Double) {
                    txt_total_price!!.text= StringBuilder("Total")
                        .append(t)

                }

                override fun onError(e: Throwable) {
                    if(!e.message!!.contains("Query returned empty"))
                        Toast.makeText(context,""+e.message!!,Toast.LENGTH_LONG).show()
                }
            })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onStart() {
        super.onStart()
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)

    }

    override fun onStop() {
        super.onStop()
        cartviewModel!!.onStop()
        compositeDisposable.clear()
        EventBus.getDefault().postSticky(HideFABCart(false))
        if(EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    fun onUpdateItemInCart(event: UpdateItemInCart){
        if(event.cartItem!=null){
            recyclerViewState= recycler_cart!!.layoutManager!!.onSaveInstanceState()
            cartDataSource!!.updatecart(event.cartItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: SingleObserver<Int>{
                    override fun onSubscribe(d: Disposable) {

                    }
                    override fun onSuccess(t: Int) {
                        calculateTotalPrice()
                        recycler_cart!!.layoutManager!!.onRestoreInstanceState(recyclerViewState)
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context,"[UPDATE CART]"+e.message,Toast.LENGTH_LONG).show()
                    }

                })
        }
    }

    private fun calculateTotalPrice()
    {
        cartDataSource!!.sumPrice(FirebaseAuth.getInstance().currentUser!!.uid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :SingleObserver<Double> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(price: Double) {
                    txt_total_price!!.text= StringBuilder("Total: ₹")
                        .append(Common.productPrice(price))
                }

                override fun onError(e: Throwable) {
                    if(!e.message!!.contains("Query returned empty"))
                        Toast.makeText(context,"[SUM CART]"+e.message,Toast.LENGTH_LONG).show()
                }

            })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {

        super.onPrepareOptionsMenu(menu)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cart_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item!!.itemId== R.id.action_clear_cart) {
            cartDataSource!!.cleanCart(FirebaseAuth.getInstance().currentUser!!.uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :SingleObserver<Int> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(t: Int) {
                        Toast.makeText(context,"Clear cart success",Toast.LENGTH_LONG).show()
                        EventBus.getDefault().postSticky(CountCartEvent(true))
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context,""+e.message,Toast.LENGTH_LONG).show()
                    }

                })
            return true
        }
        return super.onOptionsItemSelected(item)
        }
}
