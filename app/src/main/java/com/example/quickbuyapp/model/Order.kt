package com.example.quickbuyapp.model

import com.example.quickbuyapp.Database.CartItem

class Order {
    var userId : String? = null
    //var userName : String? = null
    var shippingAddress : String? = null
    //var comment : String? = null
    var transactionId : String? = null
    //var lat: Double = 0.0
    //var lng: Double = 0.0
    var finalPayment : Double = 0.0
    var totalPayment : Double = 0.0
    var isCod : Boolean = false
    var discount : Int = 0
    var cartItemList : List<CartItem>? = null
}