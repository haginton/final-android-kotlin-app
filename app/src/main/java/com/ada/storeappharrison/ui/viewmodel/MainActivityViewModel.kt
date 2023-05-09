package com.ada.storeappharrison.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.storeappharrison.network.dto.product.ProductDto
import com.ada.storeappharrison.network.service.ProductsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
    @Inject constructor(private val productsService: ProductsService): ViewModel(){

    val productQueryResultLiveData = MutableLiveData<ProductDto?>()

    fun queryProductsData(parameter: String) {
        viewModelScope.launch {
            val response = productsService.getProducts(parameter)
            if (response.isSuccessful) {
                val product = response.body()
                if (product == null) {
                    productQueryResultLiveData.postValue(null)
                } else {
                    productQueryResultLiveData.postValue(product)
                }
            } else {
                println(response.errorBody())
            }
        }
    }
}