package com.example.pos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pos.data.remote.ApiClient
import com.example.pos.data.remote.model.LoginResponse
import com.example.pos.data.remote.model.UserResponse
import com.example.pos.data.remote.repository.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnLogout: Button
    private lateinit var tvError: TextView
    private lateinit var tvRegister: TextView
    private lateinit var tvGreeting: TextView
    private lateinit var repository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = prefs.getString("token", null)

        val apiService = ApiClient.getApiService(this)
        repository = AuthRepository(apiService)

        if (token != null) {
            var tokenValid = false
            var networkError = false
            runBlocking {
                try {
                    val response: UserResponse = ApiClient.getApiService(this@MainActivity).getUserMe()
                    tokenValid = true
                    prefs.edit().putString("user_name", response.name).apply()
                } catch (e: retrofit2.HttpException) {
                    if (e.code() == 401 || e.code() == 403) {
                        prefs.edit().clear().apply()
                    } else {
                        networkError = true
                    }
                } catch (e: Exception) {
                    networkError = true
                }
            }
            if (tokenValid) {
                val intent = android.content.Intent(this, com.example.pos.ui.DashboardActivity::class.java)
                intent.putExtra("user_name", prefs.getString("user_name", "User"))
                startActivity(intent)
                finish()
                return
            } else if (networkError) {
                setContentView(R.layout.activity_main)
                Toast.makeText(this, "Tidak bisa terhubung ke server. Silakan coba lagi nanti.", Toast.LENGTH_LONG).show()
            }
        }
        // Jika token tidak valid, lanjut ke login
        setContentView(R.layout.activity_main)
        // ...existing code...
        // (jangan inisialisasi view sebelum setContentView)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnLogout = findViewById(R.id.btnLogout)
        tvError = findViewById(R.id.tvError)
        tvRegister = findViewById(R.id.tvRegister)
        tvGreeting = findViewById(R.id.tvGreeting)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                tvError.text = "Email dan password wajib diisi."
                tvError.visibility = View.VISIBLE
                return@setOnClickListener
            }
            tvError.visibility = View.GONE
            lifecycleScope.launch {
                val response: LoginResponse? = repository.login(email, password)
                if (response != null) {
                    prefs.edit().putString("token", response.token).apply()
                    // Setelah login sukses, buka DashboardActivity dan kirim nama user
                    val intent = android.content.Intent(this@MainActivity, com.example.pos.ui.DashboardActivity::class.java)
                    intent.putExtra("user_name", response.user?.name ?: "User")
                    startActivity(intent)
                    finish()
                } else {
                    tvError.text = "Login gagal. Email atau password salah."
                    tvError.visibility = View.VISIBLE
                }
            }
        }

        btnLogout.setOnClickListener {
            val token = prefs.getString("token", null)
            if (token == null) {
                Toast.makeText(this, "Token tidak ditemukan.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val result = repository.logout()
                if (result) {
                    prefs.edit().clear().apply()
                    updateUI(false)
                    Toast.makeText(this@MainActivity, "Berhasil logout", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Logout gagal. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        tvRegister.setOnClickListener {
            Toast.makeText(this, "Fitur register belum tersedia.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            etEmail.isEnabled = false
            etPassword.isEnabled = false
            btnLogin.visibility = View.GONE
            btnLogout.visibility = View.VISIBLE
            tvRegister.visibility = View.GONE
            tvGreeting.text = "Selamat datang! Anda sudah login."
        } else {
            etEmail.isEnabled = true
            etPassword.isEnabled = true
            btnLogin.visibility = View.VISIBLE
            btnLogout.visibility = View.GONE
            tvRegister.visibility = View.VISIBLE
            tvGreeting.text = "Selamat Datang di POS!"
            etPassword.setText("")
        }
        tvError.visibility = View.GONE
    }
}
