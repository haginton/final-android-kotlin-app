package com.ada.storeappharrison.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ada.storeappharrison.R
import com.ada.storeappharrison.databinding.ActivityRecyclerBinding
import com.ada.storeappharrison.databinding.ProductRowBinding
import com.ada.storeappharrison.network.dto.product.ProductDto
import com.ada.storeappharrison.network.service.ListProductsService
import com.ada.storeappharrison.storage.room.dao.ProductDao
import com.ada.storeappharrison.storage.room.entity.Product
import com.ada.storeappharrison.ui.adapter.ProductsAdapter
import com.ada.storeappharrison.ui.viewmodel.RecyclerActivityViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecyclerActivity : AppCompatActivity() {

    lateinit var viewModel: RecyclerActivityViewModel

    @Inject
    lateinit var listProductsService: ListProductsService

    @Inject
    lateinit var productDao: ProductDao

    lateinit var binding: ActivityRecyclerBinding

    private val productsAdapter = ProductsAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureRecyclerView()
        viewModel = ViewModelProvider(this)[RecyclerActivityViewModel::class.java]

        //val idProduct = "6459a2eb7fa14d501011999f"

        setClickListeners()
        addLiveDataObservers()

    }

    private fun addLiveDataObservers() {
        viewModel.productsResultLiveData.observe(this) { productsList ->
            updateProductSearchResults(
                productsList
            )
        }
    }

    private fun setClickListeners() {
        binding.searchButton.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            viewModel.queryProductsDataRecycler()
        }
    }


    /*private fun requestProductData(idProduct: String) {
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
    }*/

    private fun updateProductSearchResults(productsList: List<ProductDto>?) {
        runOnUiThread {
            binding.progressBar.visibility = View.GONE

            if (productsList != null) {
                binding.emptyView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                productsAdapter.update(productsList)
            } else {
                binding.recyclerView.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
            }

        }
    }

    private fun configureRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = productsAdapter
    }
}