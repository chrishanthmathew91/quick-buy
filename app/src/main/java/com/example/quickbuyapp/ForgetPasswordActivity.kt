package com.example.quickbuyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var mEmailReset:EditText
    private lateinit var mForgetButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        mEmailReset=findViewById(R.id.editTextEmailAddressForget)
        mForgetButton=findViewById(R.id.buttonSentEmail)
        auth=FirebaseAuth.getInstance()
        mForgetButton.setOnClickListener {
            var emailAddress: String = mEmailReset.text.toString().trim()
            if (isValidEmail(emailAddress)) {
                if (emailAddress != null) {
                    auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@ForgetPasswordActivity,
                                    "Email Sent",
                                    Toast.LENGTH_SHORT
                                ).show()
                                var intent:Intent= Intent(this@ForgetPasswordActivity,WelcomePage::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this@ForgetPasswordActivity,
                                    "Email not sent email does not exists!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}