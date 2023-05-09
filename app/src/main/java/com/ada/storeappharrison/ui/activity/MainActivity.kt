package com.ada.storeappharrison.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ada.storeappharrison.R
import com.ada.storeappharrison.databinding.ActivityMainBinding
import com.ada.storeappharrison.network.dto.product.ProductDto
import com.ada.storeappharrison.network.service.ProductsService
import com.ada.storeappharrison.ui.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var productsService: ProductsService

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        val idProduct = "6459a2eb7fa14d501011999f"

        requestProductData(idProduct)
    }

    private fun addLiveDataObservers() {
        viewModel.productQueryResultLiveData.observe(this) { productDto ->

        }
    }

    private fun requestProductData(idProduct: String) {
        GlobalScope.launch {
            val response = productsService.getProducts(idProduct)
            if (response.isSuccessful){
                Log.d("AndroidKotlinAda", "Response product with id $idProduct = ${response.body()}")
                val responseProduct: ProductDto? = response.body()
                //saveProduct(responseProduct)
            }
        }
    }
}