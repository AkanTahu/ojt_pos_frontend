package com.example.pos.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pos.R
import com.example.pos.data.remote.model.TransaksiSingleResponse

class DetailTransaksiSuksesActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi_sukses)

        val transaksi = intent.getParcelableExtra<TransaksiSingleResponse>("transaksi_detail")?.data

        val layoutProduk = findViewById<LinearLayout>(R.id.layoutProduk)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val tvLaba = findViewById<TextView>(R.id.tvLaba)
        val tvStatusPembayaran = findViewById<TextView>(R.id.tvStatusPembayaran)
        val tvMetodePembayaran = findViewById<TextView>(R.id.tvMetodePembayaran)
        val btnKembali = findViewById<Button>(R.id.btnKembali)
        val btnStruk = findViewById<Button>(R.id.btnStruk)

        // Tampilkan detail produk yang dibeli
        layoutProduk.removeAllViews()
        transaksi?.detail_transaksi?.forEach { detail ->
            val produk = detail.produk
            val tv = TextView(this)
            tv.text = "${produk?.nama} | ${detail.qty} x Rp${produk?.harga_jual} = Rp${(detail.qty?:0)*(produk?.harga_jual?:0)}\nLaba Rp${(produk?.harga_jual?:0)-(produk?.harga_pokok?:0)}"
            tv.textSize = 16f
            tv.setTextColor(resources.getColor(android.R.color.black))
            layoutProduk.addView(tv)
        }

        // Tampilkan total, laba, status, metode pembayaran
        tvTotal.text = "Total  Rp${transaksi?.total_harga ?: 0}"
        tvLaba.text = "Laba Rp${transaksi?.laba ?: 0}"
        tvStatusPembayaran.text = transaksi?.status_pembayaran?.uppercase() ?: "-"
        tvMetodePembayaran.text = transaksi?.metode_pembayaran?.replaceFirstChar { it.uppercase() } ?: "-"

        // Tombol kembali ke dashboard
        btnKembali.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        // Tombol struk (belum diisi)
        btnStruk.setOnClickListener {
            // TODO: Implementasi struk
        }
    }
}
