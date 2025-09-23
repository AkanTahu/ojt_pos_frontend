package com.example.pos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.data.remote.model.Produk

class ProdukAdapter(private val items: List<Produk>) : RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder>() {
    class ProdukViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNamaProduk)
        val tvHarga: TextView = view.findViewById(R.id.tvHargaProduk)
        val tvStok: TextView = view.findViewById(R.id.tvStokProduk)
        val tvKeterangan: TextView = view.findViewById(R.id.tvKeteranganProduk)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdukViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return ProdukViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProdukViewHolder, position: Int) {
        val produk = items[position]
        holder.tvNama.text = produk.nama
        holder.tvHarga.text = "Harga: Rp${produk.harga_jual}"
        holder.tvStok.text = "Stok: ${produk.stok}"
        holder.tvKeterangan.text = produk.keterangan ?: ""
    }

    override fun getItemCount(): Int = items.size
}