package com.example.pos.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.data.remote.ApiClient
import com.example.pos.data.remote.model.Pelanggan
import com.example.pos.data.remote.model.PelangganResponse
import kotlinx.coroutines.launch

class TransaksiBaruActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi_baru)

        val rvProdukTransaksi = findViewById<RecyclerView>(R.id.rvProdukTransaksi)
        val tvErrorProdukTransaksi = findViewById<TextView>(R.id.tvErrorProdukTransaksi)
        val tvTotalTransaksi = findViewById<TextView>(R.id.tvTotalTransaksi)
        val btnSubmitTransaksi = findViewById<TextView>(R.id.btnSubmitTransaksi)
        val btnPilihPelanggan = findViewById<Button>(R.id.btnPilihPelanggan)
        val tvNamaPelanggan = findViewById<TextView>(R.id.tvNamaPelanggan)
        rvProdukTransaksi.layoutManager = LinearLayoutManager(this)

        var pelangganId: Int? = null
        var pelangganNama: String = "-"

        btnPilihPelanggan.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val response = ApiClient.getApiService(this@TransaksiBaruActivity).getPelanggans()
                    val pelangganList = response.data
                    val namaList = pelangganList.map { it.nama }.toTypedArray()
                    AlertDialog.Builder(this@TransaksiBaruActivity)
                        .setTitle("Pilih Pelanggan")
                        .setItems(namaList) { _, which ->
                            pelangganId = pelangganList[which].id
                            pelangganNama = pelangganList[which].nama.toString()
                            tvNamaPelanggan.text = "Pelanggan: $pelangganNama"
                        }
                        .setNegativeButton("Batal", null)
                        .show()
                } catch (e: Exception) {
                    tvNamaPelanggan.text = "Gagal memuat pelanggan"
                }
            }
        }

        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@TransaksiBaruActivity).getProducts()
                if (response.data.isNullOrEmpty()) {
                    rvProdukTransaksi.visibility = View.GONE
                    tvErrorProdukTransaksi.text = "Produk masih kosong, segera tambah!"
                    tvErrorProdukTransaksi.visibility = View.VISIBLE
                } else {
                    val adapter = ProdukTransaksiAdapter(response.data, tvTotalTransaksi, btnSubmitTransaksi)
                    rvProdukTransaksi.adapter = adapter
                    rvProdukTransaksi.visibility = View.VISIBLE
                    tvErrorProdukTransaksi.visibility = View.GONE

                    btnSubmitTransaksi.setOnClickListener {
                        val produkTerpilih = adapter.getProdukTerpilih()
                        val intent = android.content.Intent(this@TransaksiBaruActivity, PaymentActivity::class.java)
                        intent.putParcelableArrayListExtra("produk_terpilih", ArrayList(produkTerpilih))
                        intent.putExtra("pelanggan_id", pelangganId)
                        intent.putExtra("total_bayar", produkTerpilih.sumOf { it.produk.harga_jual * it.qty })
                        startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                tvErrorProdukTransaksi.text = "Gagal memuat produk: ${e.localizedMessage ?: "Unknown error"}"
                tvErrorProdukTransaksi.visibility = View.VISIBLE
            }
        }
    }
}
