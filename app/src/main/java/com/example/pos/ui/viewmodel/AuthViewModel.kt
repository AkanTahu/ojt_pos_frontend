package com.example.pos.data.remote.repository

import com.example.pos.data.remote.ApiClient
import com.example.pos.data.remote.model.LoginRequest
import com.example.pos.data.remote.model.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {
    suspend fun login(email: String, password: String): LoginResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val request = LoginRequest(email, password)
                ApiClient.apiService.login(request)
            } catch (e: Exception) {
                null
            }
        }
    }
    // Add logout logic if needed
}

