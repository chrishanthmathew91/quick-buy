package com.example.quickbuyapp.ui.dashboard

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.quickbuyapp.model.repositories.UserRepository
import com.example.quickbuyapp.utils.startLoginActivity

class DashboardViewModel(private val repository: UserRepository) : ViewModel() {

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text*/
    val user by lazy {
        repository.currentUser()
    }

    fun logout(view: View) {
        repository.logout()
        view.context.startLoginActivity()
    }
}