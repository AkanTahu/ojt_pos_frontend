package com.example.pos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.data.remote.model.ProdukQty

class DetailProdukAdapter(private val produkList: List<ProdukQty>) : RecyclerView.Adapter<DetailProdukAdapter.DetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_produk, parent, false)
        return DetailViewHolder(view)
    }

    override fun getItemCount(): Int = produkList.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = produkList[position]
        holder.tvNamaProduk.text = item.produk.nama
        holder.tvHargaPokok.text = "Harga Pokok: Rp${item.produk.harga_pokok}"
        holder.tvHargaJual.text = "Harga Jual: Rp${item.produk.harga_jual}"
        holder.tvQty.text = "Qty: ${item.qty}"
        val laba = (item.produk.harga_jual - item.produk.harga_pokok) * item.qty
        holder.tvLaba.text = "Laba: Rp${laba}"
    }

    class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaProduk: TextView = view.findViewById(R.id.tvNamaProdukDetail)
        val tvHargaPokok: TextView = view.findViewById(R.id.tvHargaPokokDetail)
        val tvHargaJual: TextView = view.findViewById(R.id.tvHargaJualDetail)
        val tvQty: TextView = view.findViewById(R.id.tvQtyDetail)
        val tvLaba: TextView = view.findViewById(R.id.tvLabaDetail)
    }
}
