package com.ada.storeappharrison.network.service

import com.ada.storeappharrison.network.dto.product.ProductDto
import com.ada.storeappharrison.network.dto.product.SearchProductsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ListProductsService {
    @GET("v1/products/")
    suspend fun getProducts(): Response<SearchProductsDto>
}