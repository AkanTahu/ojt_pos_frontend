package com.example.pos.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pos.R
import com.example.pos.data.remote.ApiClient
import com.example.pos.data.remote.model.ItemTransaksi
import com.example.pos.data.remote.model.TransaksiRequest
import com.example.pos.data.remote.repository.TransaksiRepository
import kotlinx.coroutines.launch

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val tvTotalBayar = findViewById<TextView>(R.id.tvTotalBayar)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupMetode)
        val etKeterangan = findViewById<EditText>(R.id.etKeterangan)
        val btnBayar = findViewById<Button>(R.id.btnBayar)

        val totalBayar = intent.getIntExtra("total_bayar", 0)
        tvTotalBayar.text = "Rp$totalBayar"

        val pelangganId = intent.getIntExtra("pelanggan_id", -1).let { if (it == -1) null else it }

        btnBayar.setOnClickListener {
            val metodePembayaran = when (radioGroup.checkedRadioButtonId) {
                R.id.radioTunai -> "tunai"
                R.id.radioQris -> "qris"
                R.id.radioBank -> "transfer"
                else -> "tunai"
            }
            val keterangan = etKeterangan.text.toString()
            val produkList = intent.getParcelableArrayListExtra<com.example.pos.data.remote.model.ProdukQty>("produk_terpilih") ?: arrayListOf()
            val items = produkList.map { ItemTransaksi(it.produk.id, it.qty) }
            val request = TransaksiRequest(
                pelanggan_id = pelangganId,
                metode_pembayaran = metodePembayaran,
                status_pembayaran = "lunas",
                items = items,
                keterangan = keterangan
            )
            val repository = TransaksiRepository(ApiClient.getApiService(this))
            lifecycleScope.launch {
                val response = repository.submitTransaksi(request)
                Log.d("PaymentActivity", "Response: $response")
                Log.d("PaymentActivity", "Response JSON: " + com.google.gson.Gson().toJson(response))
                if (response != null) {
                    val intent = android.content.Intent(this@PaymentActivity, DetailTransaksiSuksesActivity::class.java)
                    intent.putExtra("transaksi_detail", response)
                    startActivity(intent)
                    finish()
                } else {
                    android.widget.Toast.makeText(this@PaymentActivity, "Gagal membuat transaksi", android.widget.Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}