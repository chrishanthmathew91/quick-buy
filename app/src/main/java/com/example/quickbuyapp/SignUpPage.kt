package com.example.quickbuyapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quickbuyapp.Model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class SignUpPage : AppCompatActivity() {
    lateinit var mFullNameUser:EditText
    lateinit var mEmailUser:EditText
    lateinit var mPasswordUser:EditText
    lateinit var mMobileuser:EditText
    private lateinit var auth:FirebaseAuth
    private lateinit var rootRef:DatabaseReference
    private lateinit var mSignUpButton: Button
    lateinit var mProgressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)
        mFullNameUser=findViewById(R.id.editTextPersonNameSignup)
        mEmailUser=findViewById(R.id.editTextEmailAddressSignup)
        mPasswordUser=findViewById(R.id.editTextPasswordSignup)
        mMobileuser=findViewById(R.id.editTextPhoneSignup)
        mSignUpButton=findViewById(R.id.buttonSignup)
        mProgressBar=findViewById(R.id.progressBar)
        mSignUpButton.setOnClickListener{
            var mName:String=mFullNameUser.text.toString().trim()
            var mEmail=mEmailUser.text.toString().trim()
            var mPassword = mPasswordUser.text.toString().trim()
            var mMobile = mMobileuser.text.toString().trim()
            if(isValidEmail(mEmail)) {
                if (TextUtils.isEmpty(mName)) {
                    Toast.makeText(this@SignUpPage, "Enter Name", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(mEmail)) {
                    Toast.makeText(this@SignUpPage, "Enter Email", Toast.LENGTH_SHORT).show()

                } else if (TextUtils.isEmpty(mPassword)) {
                    Toast.makeText(this@SignUpPage, "Enter Password", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(mMobile)) {
                    Toast.makeText(this@SignUpPage, "Enter Mobile", Toast.LENGTH_SHORT).show()
                } else {
                    signUpusers(mName, mEmail, mPassword, mMobile)
                    mProgressBar.visibility = View.VISIBLE
                }
            }
            else{
                Toast.makeText(this@SignUpPage, "Email Invalid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUpusers(mName: String, mEmail: String, mPassword: String, mMobile: String) {
        auth=FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(mEmail,mPassword)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    Toast.makeText(this@SignUpPage,"Sign up successful",Toast.LENGTH_SHORT).show()
                    mProgressBar.visibility=View.INVISIBLE
                }
                else{
                    Toast.makeText(this@SignUpPage,"User already exist!",Toast.LENGTH_SHORT).show()
                    mProgressBar.visibility=View.INVISIBLE
                }
            }
        rootRef=FirebaseDatabase.getInstance().getReference()
        val user= Users(mName,mEmail,mPassword,mMobile)
        rootRef.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                rootRef.child("users").child(mMobile).setValue(user)
            }

        })
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}