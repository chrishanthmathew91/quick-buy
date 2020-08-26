package com.example.quickbuyapp.Database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="Cart")
class CartItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="productId")
    var productId:String=""
    @ColumnInfo(name="productName")
    var productName:String?=null
    @ColumnInfo(name="productPrice")
    var productPrice:Double=0.0
    @ColumnInfo(name="productQuantity")
    var productQuantity:Int=0
    @ColumnInfo(name="userPhone")
    var userPhone:String?=null
    @ColumnInfo(name="uid")
    var uid:String?=null
}