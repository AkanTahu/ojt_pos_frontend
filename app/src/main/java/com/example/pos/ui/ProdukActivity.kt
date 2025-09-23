package com.example.pos.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.data.remote.ApiClient
import kotlinx.coroutines.launch

class ProdukActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produk)

        val rvProduk = findViewById<RecyclerView>(R.id.rvProduk)
        val tvError = findViewById<TextView>(R.id.tvProdukError)
        rvProduk.layoutManager = LinearLayoutManager(this)

        // app/src/main/java/com/example/pos/ui/ProdukActivity.kt
        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@ProdukActivity).getProducts()
                rvProduk.adapter = ProdukAdapter(response.data)
                tvError.visibility = View.GONE
            } catch (e: Exception) {
                tvError.text = "Gagal memuat produk: " + (e.localizedMessage ?: "Unknown error")
                tvError.visibility = View.VISIBLE
            }
        }

    }
}
