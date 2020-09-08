package com.example.quickbuyapp.Common

import com.example.quickbuyapp.model.CategoryModel
import com.example.quickbuyapp.model.ProductModel
import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.random.Random as Random

object Common {
    fun productPrice(price: Double): String {

        if(price != 0.0){
            val df = DecimalFormat("#,##0.00")
            df.roundingMode = RoundingMode.HALF_UP
            val finalPrice = StringBuilder(df.format(price)).toString()
            return finalPrice//.replace(".",",")
        }
        else
            return "0.00"

    }

    fun createOrderNumber(): String {
        return StringBuilder()
            .append(System.currentTimeMillis())
            .append(Math.abs(java.util.Random().nextInt()))
            .toString()
    }

    fun getDateOfWeek(i: Int): String {
        when(i){
            1 -> return "Monday"
            2 -> return "Tuesday"
            3 -> return "Wednesday"
            4 -> return "Thursday"
            5 -> return "Friday"
            6 -> return "Saturday"
            7 -> return "Sunday"
            else -> return "Unkown Day"
        }
    }

    fun convertStatusToText(orderStatus: Int): String {
        when(orderStatus){
            0 -> return "Placed"
            1 -> return "Shipping"
            2 -> return "Shipped"
            -1 -> return "Cancelled"
            else -> return "Unkown"
        }
    }

    val ORDER_REF: String = "Order"
    var categorySelected:CategoryModel ?= null
    var productSelected:ProductModel ?= null
    val CATEGORY_REF: String="Category"
    val FULL_WIDTH_COLUMN: Int=1
    val BEST_DEAL_REF: String = "BestDeals"
    val POPULAR_REF: String = "MostPopular"
    val DEFAULT_COLUMN_COUNT:Int=0
}
