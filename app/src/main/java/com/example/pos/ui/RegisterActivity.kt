package com.example.pos.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pos.R
import com.example.pos.data.remote.ApiClient
import com.example.pos.data.remote.repository.AuthRepository
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etToko: EditText
    private lateinit var etPassword: EditText
    private lateinit var etPasswordConfirm: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvError: TextView
    private lateinit var repository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etToko = findViewById(R.id.etToko)
        etPassword = findViewById(R.id.etPassword)
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm)
        btnRegister = findViewById(R.id.btnRegister)
        tvError = findViewById(R.id.tvError)

        val apiService = ApiClient.getApiService(this)
        repository = AuthRepository(apiService)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val toko = etToko.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val passwordConfirm = etPasswordConfirm.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || toko.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                tvError.text = "Semua field wajib diisi."
                tvError.visibility = View.VISIBLE
                return@setOnClickListener
            }
            tvError.visibility = View.GONE

            lifecycleScope.launch {
                val response = repository.register(name, email, toko, password, passwordConfirm)
                if (response != null && response.token != null) {
                    val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
                    prefs.edit().putString("token", response.token).apply()
                    val intent = Intent(this@RegisterActivity, DashboardActivity::class.java)
                    intent.putExtra("user_name", response.user?.name ?: "User")
                    startActivity(intent)
                    finish()
                } else {
                    tvError.text = "Register gagal. Cek data Anda."
                    tvError.visibility = View.VISIBLE
                }
            }
        }
    }
}