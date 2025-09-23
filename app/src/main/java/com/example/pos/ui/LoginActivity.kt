package com.example.pos.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pos.MainActivity
import com.example.pos.data.remote.model.LoginResponse
import com.example.pos.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import com.example.pos.R

class LoginActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvError = findViewById<TextView>(R.id.tvError)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            Log.d("LoginActivity", "Tombol login diklik dengan email: $email")
            lifecycleScope.launch {
                val response: LoginResponse? = viewModel.loginSuspend(email, password)
                Log.d("LoginActivity", "Response dari loginSuspend: $response")
                if (response != null) {
                    val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
                    prefs.edit().putString("token", response.token).apply()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    tvError.text = "Login gagal. Email atau password salah."
                    tvError.visibility = TextView.VISIBLE
                }
            }
        }
    }
}
