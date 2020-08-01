package com.example.quickbuyapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    lateinit var mEmailAddressLoginPage:EditText
    lateinit var mPasswordlogin:EditText
    lateinit var mButton: Button
    lateinit var mProgressBar: ProgressBar
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        mEmailAddressLoginPage=findViewById(R.id.editTextEmailAddressLogin)
        mPasswordlogin=findViewById(R.id.editTextPasswordLogin)
        mButton=findViewById(R.id.buttonLogin)
        mProgressBar=findViewById(R.id.progressBarLogin)
        mButton.setOnClickListener{
            var mEmail=mEmailAddressLoginPage.text.toString()
            var mPassword=mPasswordlogin.text.toString()
            if(TextUtils.isEmpty(mEmail)){
                Toast.makeText(this@LoginPage,"Enter Email", Toast.LENGTH_SHORT).show()
            }
            else if(TextUtils.isEmpty(mPassword)) {
                Toast.makeText(this@LoginPage,"Enter Email", Toast.LENGTH_SHORT).show()
            }
            else{
                validateLogin(mEmail,mPassword)
                mProgressBar.visibility= View.VISIBLE
            }
        }

    }

    private fun validateLogin(mEmail: String, mPassword: String) {
        auth=FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(mEmail,mPassword)
            .addOnCompleteListener (this){task ->
                if(task.isSuccessful){
                    Toast.makeText(this@LoginPage,"Login in successful",Toast.LENGTH_SHORT).show()
                    mProgressBar.visibility=View.INVISIBLE
                }
                else{
                    Toast.makeText(this@LoginPage,"Login in successful",Toast.LENGTH_SHORT).show()
                    mProgressBar.visibility=View.INVISIBLE
                }
            }
    }
}