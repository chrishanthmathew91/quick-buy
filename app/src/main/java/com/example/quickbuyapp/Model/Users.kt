package com.example.quickbuyapp.Model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(val username:String?="",
            val userEmail:String?="",
            val userPassword:String?="",
            val userMobile:String?="") {
}