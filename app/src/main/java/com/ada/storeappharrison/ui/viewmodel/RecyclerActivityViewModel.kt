package com.ada.storeappharrison.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.storeappharrison.network.dto.product.ProductDto
import com.ada.storeappharrison.network.service.ListProductsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecyclerActivityViewModel
    @Inject constructor(private val listProductsService: ListProductsService): ViewModel(){

    val productsResultLiveData = MutableLiveData<List<ProductDto>?>()

    fun queryProductsDataRecycler() {
        viewModelScope.launch {
            val response = listProductsService.getProducts()
            Log.d("AndroidKotlinAda", "response : $response")
            if (response.isSuccessful) {
                val products = response.body()
                val content = products!!.content
                Log.d("AndroidKotlinAda", "varlor de product : $products")
                if (content == null || content.isEmpty()) {
                    Log.d("AndroidKotlinAda", "Entra null interno")
                    productsResultLiveData.postValue(null)
                } else {
                    Log.d("AndroidKotlinAda", "adiciona product")
                    productsResultLiveData.postValue(content)
                }
            } else {
                Log.d("AndroidKotlinAda", "Invocacion product no exitosa")
                productsResultLiveData.postValue(null)
                println(response.errorBody())
            }
        }
    }
}