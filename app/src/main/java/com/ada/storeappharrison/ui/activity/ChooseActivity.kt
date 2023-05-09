package com.ada.storeappharrison.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ada.storeappharrison.databinding.ActivityChooseBinding
import com.ada.storeappharrison.databinding.ActivityLoginBinding

class ChooseActivity: AppCompatActivity() {

    private lateinit var binding: ActivityChooseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        binding = ActivityChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListeners()

    }

    private fun setClickListeners(){
        binding.searchById.setOnClickListener{
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        binding.searchAll.setOnClickListener {
            Intent(this, RecyclerActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

}