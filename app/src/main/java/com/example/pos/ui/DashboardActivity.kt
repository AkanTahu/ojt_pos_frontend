package com.example.pos.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pos.MainActivity
import com.example.pos.R
import com.example.pos.data.remote.repository.AuthRepository
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {
    private val repository = AuthRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val userName = intent.getStringExtra("user_name") ?: "User"
        tvWelcome.text = "Selamat datang, $userName!"

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
            val token = prefs.getString("token", null)
            if (token == null) {
                Toast.makeText(this, "Token tidak ditemukan.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            btnLogout.isEnabled = false
            btnLogout.text = "Logout..."
            lifecycleScope.launch {
                val result = repository.logout(token)
                btnLogout.isEnabled = true
                btnLogout.text = "Logout"
                if (result) {
                    prefs.edit().clear().apply()
                    Toast.makeText(this@DashboardActivity, "Berhasil logout", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@DashboardActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@DashboardActivity, "Logout gagal. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
