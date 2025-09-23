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
import com.example.pos.data.remote.ApiClient
import com.example.pos.data.remote.repository.AuthRepository
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {
    private lateinit var repository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val apiService = ApiClient.getApiService(this)
        repository = AuthRepository(apiService)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val userName = intent.getStringExtra("user_name") ?: "User"
        tvWelcome.text = "Selamat datang, $userName!"

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
            lifecycleScope.launch {
                val result = repository.logout()
                if (result) {
                    prefs.edit().clear().apply()
                    // Go back to MainActivity after logout
                    val intent = Intent(this@DashboardActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }

        val btnProduk = findViewById<Button>(R.id.btnMenu4)
        btnProduk.setOnClickListener {
            startActivity(Intent(this, ProdukActivity::class.java))
        }

        val btnTransaksi = findViewById<Button>(R.id.btnMenu1)
        btnTransaksi.setOnClickListener {
            startActivity(Intent(this, TransaksiActivity::class.java))
        }
    }
}
