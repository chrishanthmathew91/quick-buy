package com.example.quickbuyapp.Common

import com.example.quickbuyapp.model.CategoryModel
import com.example.quickbuyapp.model.ProductModel
import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat

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

    var categorySelected:CategoryModel ?= null
    var productSelected:ProductModel ?= null
    val CATEGORY_REF: String="Category"
    val FULL_WIDTH_COLUMN: Int=1
    val BEST_DEAL_REF: String = "BestDeals"
    val POPULAR_REF: String = "MostPopular"
    val DEFAULT_COLUMN_COUNT:Int=0
}
