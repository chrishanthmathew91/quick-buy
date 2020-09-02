package com.example.quickbuyapp.ui.cart

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickbuyapp.Database.CartDataSource
import com.example.quickbuyapp.Database.CartDatabase
import com.example.quickbuyapp.Database.CartItem
import com.example.quickbuyapp.Database.LocalCartDataSource
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CartViewModel : ViewModel() {
    private val compositeDisposable:CompositeDisposable
    private var cartDataSource:CartDataSource?=null
    private var mutableLiveDataCartItem:MutableLiveData<List<CartItem>>?=null
    init {
        compositeDisposable= CompositeDisposable()
    }
    fun initCartDataSource(context: Context){
        cartDataSource= LocalCartDataSource(CartDatabase.getInstance(context).cartDAO())
    }
    fun getMutableLiveDataCartItem():MutableLiveData<List<CartItem>>{
        if(mutableLiveDataCartItem==null)
            mutableLiveDataCartItem= MutableLiveData()
        getCartItems()
        return mutableLiveDataCartItem!!

    }
    private fun getCartItems(){
        compositeDisposable.addAll(cartDataSource!!.getAllCart(FirebaseAuth.getInstance().currentUser!!.uid!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({cartItems ->
                mutableLiveDataCartItem!!.value= cartItems
            },{t:Throwable -> mutableLiveDataCartItem!!.value = null
            }))
    }
    fun onStop(){
        compositeDisposable.clear()
    }
}