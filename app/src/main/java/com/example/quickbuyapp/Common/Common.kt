package com.example.quickbuyapp.Common

import com.example.quickbuyapp.model.CategoryModel
import com.example.quickbuyapp.model.ProductModel

object Common {
    var categorySelected:CategoryModel ?= null
    var productSelected:ProductModel ?= null
    val CATEGORY_REF: String="Category"
    val FULL_WIDTH_COLUMN: Int=1
    val BEST_DEAL_REF: String = "BestDeals"
    val POPULAR_REF: String = "MostPopular"
    val DEFAULT_COLUMN_COUNT:Int=0
}
