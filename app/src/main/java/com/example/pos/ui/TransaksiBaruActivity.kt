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

class TransaksiBaruActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi_baru)

        val rvProdukTransaksi = findViewById<RecyclerView>(R.id.rvProdukTransaksi)
        val tvErrorProdukTransaksi = findViewById<TextView>(R.id.tvErrorProdukTransaksi)
        val tvTotalTransaksi = findViewById<TextView>(R.id.tvTotalTransaksi)
        val btnSubmitTransaksi = findViewById<TextView>(R.id.btnSubmitTransaksi)
        rvProdukTransaksi.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@TransaksiBaruActivity).getProducts()
                if (response.data.isNullOrEmpty()) {
                    rvProdukTransaksi.visibility = View.GONE
                    tvErrorProdukTransaksi.text = "Produk masih kosong, segera tambah!"
                    tvErrorProdukTransaksi.visibility = View.VISIBLE
                } else {
                    rvProdukTransaksi.adapter = ProdukTransaksiAdapter(response.data, tvTotalTransaksi, btnSubmitTransaksi)
                    rvProdukTransaksi.visibility = View.VISIBLE
                    tvErrorProdukTransaksi.visibility = View.GONE
                }
            } catch (e: Exception) {
                tvErrorProdukTransaksi.text = "Gagal memuat produk: ${e.localizedMessage ?: "Unknown error"}"
                tvErrorProdukTransaksi.visibility = View.VISIBLE
            }
        }
    }
}

