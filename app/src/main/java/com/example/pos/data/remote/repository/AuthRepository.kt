package com.example.pos.data.remote.repository

import android.util.Log
import com.example.pos.data.remote.ApiService
import com.example.pos.data.remote.model.LoginRequest
import com.example.pos.data.remote.model.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val apiService: ApiService) {
    suspend fun login(email: String, password: String): LoginResponse? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("AuthRepository", "Mengirim request login ke backend dengan email: $email")
                val request = LoginRequest(email, password)
                val response = apiService.login(request)
                Log.d("AuthRepository", "Response dari backend: $response")
                response
            } catch (e: Exception) {
                Log.e("AuthRepository", "Error saat login: ", e)
                null
            }
        }
    }

    suspend fun logout(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("AuthRepository", "Mengirim request logout ke backend")
                val response = apiService.logout()
                Log.d("AuthRepository", "Response logout: ${response.code()}")
                response.isSuccessful
            } catch (e: Exception) {
                Log.e("AuthRepository", "Error saat logout: ", e)
                false
            }
        }
    }

    suspend fun register(name: String, email: String, toko: String, password: String, passwordConfirmation: String): LoginResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val requestBody = mapOf(
                    "name" to name,
                    "email" to email,
                    "toko" to toko,
                    "password" to password,
                    "password_confirmation" to passwordConfirmation
                )
                Log.d("AuthRepository", "Request register: $requestBody")
                val response = apiService.register(requestBody)
                Log.d("AuthRepository", "Response register: $response")
                response
            } catch (e: Exception) {
                Log.e("AuthRepository", "Error saat register: ", e)
                null
            }
        }
    }
}