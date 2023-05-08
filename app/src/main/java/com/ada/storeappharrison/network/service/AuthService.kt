package com.ada.storeappharrison.network.service

import com.ada.storeappharrison.network.dto.auth.LoginDto
import com.ada.storeappharrison.network.dto.auth.TokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("v1/auth")
    suspend fun login(@Body loginDto: LoginDto): Response<TokenDto>
}