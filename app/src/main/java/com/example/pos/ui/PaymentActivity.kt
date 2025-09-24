package com.example.pos.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pos.R

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

        btnBayar.setOnClickListener {
            val metodePembayaran = when (radioGroup.checkedRadioButtonId) {
                R.id.radioTunai -> "tunai"
                R.id.radioQris -> "qris"
                R.id.radioBank -> "transfer"
                else -> "tunai"
            }
            val keterangan = etKeterangan.text.toString()
            // TODO: Lanjutkan ke POST /transaksi
        }
    }
}