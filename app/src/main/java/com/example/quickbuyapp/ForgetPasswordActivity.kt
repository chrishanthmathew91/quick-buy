package com.example.quickbuyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        var emailAddress:String=mEmailReset.text.toString().trim()
        auth=FirebaseAuth.getInstance()
        mForgetButton.setOnClickListener {
            auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        Toast.makeText(this@ForgetPasswordActivity,"Email Sent",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@ForgetPasswordActivity,"Email not sent email does not exists!",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}