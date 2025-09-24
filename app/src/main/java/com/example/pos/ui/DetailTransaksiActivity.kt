package com.example.pos.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.data.remote.model.Produk
import com.example.pos.data.remote.model.ProdukQty

class DetailTransaksiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)

        val rvDetailProduk = findViewById<RecyclerView>(R.id.rvDetailProduk)
        val tvTotalHarga = findViewById<TextView>(R.id.tvTotalHarga)
        val tvTotalLaba = findViewById<TextView>(R.id.tvTotalLaba)
        val btnPembayaran = findViewById<Button>(R.id.btnPembayaran)

        val produkList = intent.getParcelableArrayListExtra<ProdukQty>("produk_terpilih") ?: arrayListOf()
        rvDetailProduk.layoutManager = LinearLayoutManager(this)
        rvDetailProduk.adapter = DetailProdukAdapter(produkList)

        var totalHarga = 0
        var totalLaba = 0
        produkList.forEach {
            totalHarga += it.produk.harga_jual * it.qty
            totalLaba += (it.produk.harga_jual - it.produk.harga_pokok) * it.qty
        }
        tvTotalHarga.text = "Total: Rp${totalHarga}"
        tvTotalLaba.text = "Laba: Rp${totalLaba}"

        btnPembayaran.setOnClickListener {
            val intent = android.content.Intent(this, PaymentActivity::class.java)
            intent.putExtra("total_bayar", totalHarga)
            intent.putParcelableArrayListExtra("produk_terpilih", produkList)
            startActivity(intent)
        }
    }
}