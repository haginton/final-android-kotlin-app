package com.ada.storeappharrison.network

import com.ada.storeappharrison.storage.sharedpreferences.StorageToken
import okhttp3.Interceptor
import okhttp3.Response

class JWTInterceptor(private val storageToken: StorageToken): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = storageToken.getToken()
        if (token?.isNotEmpty()!!){
            request.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(request.build())
    }
}