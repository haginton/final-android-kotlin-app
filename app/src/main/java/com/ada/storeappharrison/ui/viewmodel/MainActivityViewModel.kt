package com.ada.storeappharrison.ui.viewmodel

import android.util.Log
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
        Log.d("AndroidKotlinAda", "Valor de parameter: $parameter")
        viewModelScope.launch {
            val response = productsService.getProducts(parameter)
            Log.d("AndroidKotlinAda", "response : $response")
            if (response.isSuccessful) {
                Log.d("AndroidKotlinAda", "Invocacion product exitosa: $parameter")
                val product = response.body()
                Log.d("AndroidKotlinAda", "varlor de product : $product")
                if (product!!.name == null) {
                    Log.d("AndroidKotlinAda", "Entra null interno")
                    productQueryResultLiveData.postValue(null)
                } else {
                    Log.d("AndroidKotlinAda", "adiciona product")
                    productQueryResultLiveData.postValue(product)
                }
            } else {
                Log.d("AndroidKotlinAda", "Invocacion product no exitosa: $parameter")
                productQueryResultLiveData.postValue(null)
                println(response.errorBody())
            }
        }
    }
}