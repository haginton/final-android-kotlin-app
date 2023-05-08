package com.ada.storeappharrison.storage.sharedpreferences

interface Storage {

    fun saveToken(token: String)

    fun getToken(): String?

    fun isAuthenticated(): Boolean?

    fun clearToken()
}