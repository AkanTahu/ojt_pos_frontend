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
import com.example.pos.data.remote.model.LoginResponse
import com.example.pos.data.remote.repository.AuthRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnLogout: Button
    private lateinit var tvError: TextView
    private lateinit var tvRegister: TextView
    private lateinit var tvGreeting: TextView
    private val repository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnLogout = findViewById(R.id.btnLogout)
        tvError = findViewById(R.id.tvError)
        tvRegister = findViewById(R.id.tvRegister)
        tvGreeting = findViewById(R.id.tvGreeting)

        val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = prefs.getString("token", null)
        updateUI(token != null)

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
                val result = repository.logout(token)
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
