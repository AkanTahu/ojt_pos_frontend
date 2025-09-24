package com.example.pos.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pos.R
import com.example.pos.data.remote.model.TransaksiSingleResponse

class DetailTransaksiSuksesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi_sukses)

        val tvDetailTransaksi = findViewById<TextView>(R.id.tvDetailTransaksi)
        val transaksi = intent.getParcelableExtra<TransaksiSingleResponse>("transaksi_detail")
        tvDetailTransaksi.text = transaksi?.data?.toString() ?: "Transaksi tidak ditemukan"
    }
}
