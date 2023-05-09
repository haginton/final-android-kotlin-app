package com.ada.storeappharrison.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ada.storeappharrison.R
import com.ada.storeappharrison.databinding.ActivityLoginBinding
import com.ada.storeappharrison.network.dto.auth.LoginDto
import com.ada.storeappharrison.network.dto.auth.TokenDto
import com.ada.storeappharrison.network.service.AuthService
import com.ada.storeappharrison.storage.sharedpreferences.StorageToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    @Inject
    lateinit var authService: AuthService

    @Inject
    lateinit var storageToken: StorageToken

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListeners()
    }

    private fun setClickListeners(){
        binding.buttonLogin.setOnClickListener{
            val email: String = binding.emailAddress.text.toString()
            val password: String = binding.password.text.toString()
            Log.d("AndroidKotlinAda", "user: $email, pass: $password")
            auth(email, password)
        }
    }

    private fun auth(email: String, password : String) {
        GlobalScope.launch {
            val loginDto = LoginDto(
                email,
                password
            )

            val response: Response<TokenDto> = authService.login(loginDto)
            if (response.isSuccessful){
                val token: TokenDto? = response.body()
                Log.d("AndroidKotlinAda", "Login success with the token: ${token?.token}")
                storageToken.saveToken(token!!.token)
                Log.d("AndroidKotlinAda", "shared preferences saved ok with the token from shared preferences: ${storageToken.getToken()}")
                runOnUiThread {
                    Intent(this@LoginActivity, MainActivity::class.java).also {
                        startActivity(it)
                        Log.d("AndroidKotlinAda", "After Login success the MainActivity will open, because the JWT is not empty: ${storageToken.getToken()}")
                        finish()
                    }
                }
            }else {
                //codigo de respuesta si hay error en la autenticación para generar el JWT
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, getText(R.string.incorrect_credentials), Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}