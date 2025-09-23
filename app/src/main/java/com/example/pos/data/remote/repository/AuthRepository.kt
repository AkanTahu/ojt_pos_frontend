package com.example.pos.data.remote.repository

import android.util.Log
import com.example.pos.data.remote.ApiClient
import com.example.pos.data.remote.model.LoginRequest
import com.example.pos.data.remote.model.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {
    suspend fun login(email: String, password: String): LoginResponse? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("AuthRepository", "Mengirim request login ke backend dengan email: $email")
                val request = LoginRequest(email, password)
                val response = ApiClient.apiService.login(request)
                Log.d("AuthRepository", "Response dari backend: $response")
                response
            } catch (e: Exception) {
                Log.e("AuthRepository", "Error saat login: ", e)
                null
            }
        }
    }

    suspend fun logout(token: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("AuthRepository", "Mengirim request logout ke backend dengan token: $token")
                val response = ApiClient.apiService.logout("Bearer $token")
                Log.d("AuthRepository", "Response logout: ${response.code()}")
                response.isSuccessful
            } catch (e: Exception) {
                Log.e("AuthRepository", "Error saat logout: ", e)
                false
            }
        }
    }
}
