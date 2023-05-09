package com.ada.storeappharrison.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ada.storeappharrison.R
import com.ada.storeappharrison.databinding.ActivityMainBinding
import com.ada.storeappharrison.network.dto.product.ProductDto
import com.ada.storeappharrison.network.service.ProductsService
import com.ada.storeappharrison.storage.room.dao.ProductDao
import com.ada.storeappharrison.storage.room.entity.Product
import com.ada.storeappharrison.ui.viewmodel.MainActivityViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var productsService: ProductsService

    @Inject
    lateinit var productDao: ProductDao

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        //val idProduct = "6459a2eb7fa14d501011999f"

        setClickListeners()
        addLiveDataObservers()
    }

    private fun addLiveDataObservers() {
        viewModel.productQueryResultLiveData.observe(this) { productDto ->
            updateProductSearchResults(
                productDto
            )
        }
    }

    private fun setClickListeners() {
        binding.searchButton.setOnClickListener {
            viewModel.queryProductsData(binding.searchQuery.text.toString())
            requestProductData(binding.searchQuery.text.toString())
        }
    }


    private fun requestProductData(idProduct: String) {
        GlobalScope.launch {
            val response = productsService.getProducts(idProduct)
            if (response.isSuccessful){
                Log.d("AndroidKotlinAda", "Response product with id $idProduct = ${response.body()}")
                val responseProduct: ProductDto? = response.body()
                saveProduct(responseProduct)
            }
        }
    }

    private fun saveProduct(productDto: ProductDto?){
        GlobalScope.launch {
            val product = Product(
                productDto?.category,
                productDto?.description,
                productDto?.imageUrl,
                productDto?.name,
                productDto?.price
            )

            productDao.insertAllProducts(product)
            Log.d("AndroidKotlinAda", "Product saved with success")
        }
    }

    private fun updateProductSearchResults(productDto: ProductDto?) {
        Log.d("AndroidKotlinAda", "Resultado producDto: $productDto")
        runOnUiThread {
            if (productDto == null) {
                Log.d("AndroidKotlinAda", "producDto es null: $productDto")
                binding.productName.text = getText(R.string.no_product_found)
                binding.productDescription.visibility = View.INVISIBLE
                binding.productPrice.visibility = View.INVISIBLE
                binding.productPoster.visibility = View.INVISIBLE

            } else {

                binding.productName.text = productDto.name
                binding.productDescription.visibility = View.VISIBLE
                binding.productPrice.visibility = View.VISIBLE
                binding.productPoster.visibility = View.VISIBLE
                binding.productDescription.text = productDto.description
                binding.productPrice.text = "$ ${productDto.price.toString()}"
                Glide.with(application).load(productDto.imageUrl)
                    .into(binding.productPoster)
            }
        }
    }
}