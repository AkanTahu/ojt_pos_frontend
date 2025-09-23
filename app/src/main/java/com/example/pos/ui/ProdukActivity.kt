package com.example.pos.ui

import android.annotation.SuppressLint
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
import android.app.AlertDialog
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.pos.data.remote.model.TambahProdukRequest
import com.example.pos.data.remote.model.Produk
import android.widget.LinearLayout

class ProdukActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produk)

        val rvProduk = findViewById<RecyclerView>(R.id.rvProduk)
        val tvError = findViewById<TextView>(R.id.tvProdukError)
        val layoutContent = findViewById<LinearLayout>(R.id.layoutProdukContent)
        val tvKosongProduk = findViewById<TextView>(R.id.tvKosongProduk)
        rvProduk.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@ProdukActivity).getProducts()
                if (response.data.isNullOrEmpty()) {
                    layoutContent.visibility = View.GONE
                    tvKosongProduk.visibility = View.VISIBLE
                } else {
                    rvProduk.adapter = ProdukAdapter(response.data)
                    layoutContent.visibility = View.VISIBLE
                    tvKosongProduk.visibility = View.GONE
                    rvProduk.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                }
            } catch (e: Exception) {
                tvError.text = "Gagal memuat produk: " + (e.localizedMessage ?: "Unknown error")
                tvError.visibility = View.VISIBLE
            }
        }

        val fab = findViewById<View>(R.id.fabTambahProduk)
        fab.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_tambah_produk, null)
            AlertDialog.Builder(this)
                .setTitle("Tambah Produk")
                .setView(dialogView)
                .setPositiveButton("Submit") { dialog, _ ->
                    val prefs = getSharedPreferences("auth", MODE_PRIVATE)

                    val nama = dialogView.findViewById<EditText>(R.id.etNamaProduk).text.toString()
                    val hargaPokok = dialogView.findViewById<EditText>(R.id.etHargaPokok).text.toString().toIntOrNull() ?: 0
                    val hargaJual = dialogView.findViewById<EditText>(R.id.etHargaJual).text.toString().toIntOrNull() ?: 0
                    val stok = dialogView.findViewById<EditText>(R.id.etStok).text.toString()
                    val isProdukStok = dialogView.findViewById<CheckBox>(R.id.cbProdukStok).isChecked
                    val isGantiStok = dialogView.findViewById<CheckBox>(R.id.cbGantiStok).isChecked
                    val gambar = dialogView.findViewById<EditText>(R.id.etGambar).text.toString().ifBlank { null }
                    val keterangan = dialogView.findViewById<EditText>(R.id.etKeterangan).text.toString().ifBlank { null }

                    val request = TambahProdukRequest(
                        nama = nama,
                        harga_pokok = hargaPokok,
                        harga_jual = hargaJual,
                        stok = stok,
                        is_produk_stok = isProdukStok,
                        is_ganti_stok = isGantiStok,
                        gambar = gambar,
                        keterangan = keterangan
                    )

                    lifecycleScope.launch {
                        try {
                            val produkBaru: Produk = ApiClient.getApiService(this@ProdukActivity).tambahProduk(request)
                            Toast.makeText(this@ProdukActivity, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                            // Refresh produk list (bisa dengan reload activity atau update adapter)
                            recreate()
                        } catch (e: Exception) {
                            Toast.makeText(this@ProdukActivity, "Gagal menambah produk: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                        }
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                .show()
        }

    }
}
