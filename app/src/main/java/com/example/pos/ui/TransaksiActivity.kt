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

class TransaksiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)

        val rvTransaksi = findViewById<RecyclerView>(R.id.rvTransaksi)
        val tvError = findViewById<TextView>(R.id.tvTransaksiError)
        val fabTambahTransaksi = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabTambahTransaksi)
        rvTransaksi.layoutManager = LinearLayoutManager(this)

        fabTambahTransaksi.visibility = View.VISIBLE
        fabTambahTransaksi.setOnClickListener {
            val intent = android.content.Intent(this, TransaksiBaruActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@TransaksiActivity).getTransaksis()
                if (response.data.isNullOrEmpty()) {
                    rvTransaksi.visibility = View.GONE
                    tvError.text = "Belum ada transaksi."
                    tvError.visibility = View.VISIBLE
                } else {
                    rvTransaksi.adapter = TransaksiAdapter(response.data)
                    rvTransaksi.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                }
            } catch (e: Exception) {
                tvError.text = "Gagal memuat transaksi: ${e.localizedMessage ?: "Unknown error"}"
                tvError.visibility = View.VISIBLE
            }
        }
    }
}
