package com.example.pos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.data.remote.model.Produk

class ProdukTransaksiAdapter(
    private val produkList: List<Produk>,
    private val tvTotalTransaksi: TextView,
    private val btnSubmitTransaksi: TextView
) : RecyclerView.Adapter<ProdukTransaksiAdapter.ProdukViewHolder>() {
    private val qtyMap = mutableMapOf<Int, Int>()

    init {
        produkList.forEach { qtyMap[it.id] = 0 }
        updateTotal()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdukViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produk_transaksi, parent, false)
        return ProdukViewHolder(view)
    }

    override fun getItemCount(): Int = produkList.size

    override fun onBindViewHolder(holder: ProdukViewHolder, position: Int) {
        val produk = produkList[position]
        holder.tvNamaProduk.text = produk.nama
        holder.tvHargaProduk.text = "Rp${produk.harga_jual}"
        holder.tvQty.text = qtyMap[produk.id].toString()
        holder.btnTambah.setOnClickListener {
            qtyMap[produk.id] = (qtyMap[produk.id] ?: 0) + 1
            holder.tvQty.text = qtyMap[produk.id].toString()
            updateTotal()
        }
        holder.btnKurang.setOnClickListener {
            val currentQty = qtyMap[produk.id] ?: 0
            if (currentQty > 0) {
                qtyMap[produk.id] = currentQty - 1
                holder.tvQty.text = qtyMap[produk.id].toString()
                updateTotal()
            }
        }
    }

    private fun updateTotal() {
        var total = 0
        var jumlahProduk = 0
        produkList.forEach {
            val qty = qtyMap[it.id] ?: 0
            if (qty > 0) {
                total += qty * it.harga_jual
                jumlahProduk += qty
            }
        }
        tvTotalTransaksi.text = "Total: Rp$total"
        btnSubmitTransaksi.text = "${jumlahProduk} Produk | Rp$total"
    }

    class ProdukViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaProduk: TextView = view.findViewById(R.id.tvNamaProduk)
        val tvHargaProduk: TextView = view.findViewById(R.id.tvHargaProduk)
        val tvQty: TextView = view.findViewById(R.id.tvQty)
        val btnTambah: Button = view.findViewById(R.id.btnTambah)
        val btnKurang: Button = view.findViewById(R.id.btnKurang)
    }
}

