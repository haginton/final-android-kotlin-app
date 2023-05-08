package com.ada.storeappharrison.storage.sharedpreferences

interface StorageToken {
    fun saveToken(token: String)

    fun getToken(): String?

    fun isAuthenticated(): Boolean?

    fun clearToken()
}