package com.example.pos.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pos.data.remote.repository.AuthRepository
import com.example.pos.data.remote.model.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()
    private val _loginState = MutableStateFlow<LoginResponse?>(null)
    val loginState: StateFlow<LoginResponse?> = _loginState

    fun login(email: String, password: String) {
        Log.d("AuthViewModel", "login() dipanggil dengan email: $email")
        viewModelScope.launch {
            val response = repository.login(email, password)
            Log.d("AuthViewModel", "Response dari repository.login: $response")
            _loginState.value = response
        }
    }

    suspend fun loginSuspend(email: String, password: String): LoginResponse? {
        Log.d("AuthViewModel", "loginSuspend() dipanggil dengan email: $email")
        val response = repository.login(email, password)
        Log.d("AuthViewModel", "Response dari repository.login (suspend): $response")
        return response
    }
}
