package com.example.quickbuyapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.quickbuyapp.R
import com.example.quickbuyapp.databinding.ActivityLoginPageBinding
import com.example.quickbuyapp.ui.dashboard.UserDashboard
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    private lateinit var binding:ActivityLoginPageBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login_page)
        binding.buttonLogin.setOnClickListener {
            var mEmail =binding.editTextEmailAddressLogin.text.toString().trim()
            var mPassword =binding.editTextPasswordLogin.text.toString().trim()
            if (isValidEmail(mEmail)) {
                if (TextUtils.isEmpty(mEmail)) {
                    Toast.makeText(this@LoginPage, "Enter Email", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(mPassword)) {
                    Toast.makeText(this@LoginPage, "Enter Password", Toast.LENGTH_SHORT).show()
                } else {
                    validateLogin(mEmail, mPassword)
                    binding.progressBarLogin.visibility = View.VISIBLE
                }
            }
            else{
                Toast.makeText(this@LoginPage, "Email Invalid", Toast.LENGTH_SHORT).show()
            }
        }
        binding.textViewForgetPassword.setOnClickListener {
            var intent:Intent= Intent(this@LoginPage,
                ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
    }
    private fun validateLogin(mEmail: String, mPassword: String) {
        auth=FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(mEmail,mPassword)
            .addOnCompleteListener (this){task ->
                if(task.isSuccessful){
                    Toast.makeText(this@LoginPage,"Login in successful",Toast.LENGTH_SHORT).show()
                    val intent:Intent= Intent(this@LoginPage,
                        UserDashboard::class.java)
                    startActivity(intent)
                    binding.progressBarLogin.visibility=View.GONE
                }
                else{
                    Toast.makeText(this@LoginPage,"Login in unsuccesful",Toast.LENGTH_SHORT).show()
                    binding.progressBarLogin.visibility=View.GONE
                }
            }
    }
    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}