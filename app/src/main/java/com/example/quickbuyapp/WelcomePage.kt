package com.example.quickbuyapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.quickbuyapp.ui.auth.LoginPage
import com.example.quickbuyapp.ui.auth.SignUpPage

class WelcomePage : AppCompatActivity() {
    lateinit var mSignUpButton: Button
    lateinit var mLoginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)
        mSignUpButton=findViewById(R.id.buttonSignup)
        mLoginButton=findViewById(R.id.buttonLogin)
        mSignUpButton.setOnClickListener{
            var context:Context=this@WelcomePage
            var destinationActivity= SignUpPage::class.java
            var intent: Intent =Intent(context,destinationActivity)
            startActivity(intent)
        }
        mLoginButton.setOnClickListener {
            var context:Context=this@WelcomePage
            var destinationActivity= LoginPage::class.java
            var intent: Intent =Intent(context,destinationActivity)
            startActivity(intent)
        }

    }
}