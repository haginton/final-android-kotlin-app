package com.ada.storeappharrison.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ada.storeappharrison.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}