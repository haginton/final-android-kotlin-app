package com.ada.storeappharrison.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ada.storeappharrison.storage.sharedpreferences.StorageToken
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LauncherActivity: AppCompatActivity() {

    @Inject
    lateinit var storageToken: StorageToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (storageToken.isAuthenticated()!!){
            Intent(this, ChooseActivity::class.java).also {
                startActivity(it)
                Log.d("AndroidKotlinAda", "The activity ChooseActivity will open, because the JWT is not empty: ${storageToken.getToken()}")
                finish()
            }
        }else {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                Log.d("AndroidKotlinAda", "The activity LoginActivity will open, because the JWT is empty")
                finish()
            }
        }
    }

}