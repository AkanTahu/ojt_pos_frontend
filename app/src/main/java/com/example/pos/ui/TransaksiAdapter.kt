package com.example.pos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.data.remote.model.Transaksi

class TransaksiAdapter(private val list: List<Transaksi>) : RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaksi, parent, false)
        return TransaksiViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        val transaksi = list[position]
        holder.tvTransaksiId.text = "ID Transaksi: ${transaksi.id}"
        holder.tvTransaksiTanggal.text = transaksi.created_at?.let { formatTanggal(it) }
        holder.tvTransaksiTotal.text = "Total: Rp${transaksi.total_harga}"
        holder.tvTransaksiStatus.text = "Status: ${transaksi.status_pembayaran}"
        holder.tvTransaksiDetail.text = transaksi.detail_transaksi.joinToString("\n") {
            "- ${it.produk.nama} x${it.qty}"
        }
    }

    override fun getItemCount(): Int = list.size

    class TransaksiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTransaksiId: TextView = view.findViewById(R.id.tvTransaksiId)
        val tvTransaksiTanggal: TextView = view.findViewById(R.id.tvTransaksiTanggal)
        val tvTransaksiTotal: TextView = view.findViewById(R.id.tvTransaksiTotal)
        val tvTransaksiStatus: TextView = view.findViewById(R.id.tvTransaksiStatus)
        val tvTransaksiDetail: TextView = view.findViewById(R.id.tvTransaksiDetail)
    }

    private fun formatTanggal(dateTime: String): String {
        // Format: "2025-09-23T17:17:18.000000Z" -> "23-09-2025 17:17"
        return try {
            val tanggal = dateTime.substring(0, 10)
            val jam = dateTime.substring(11, 16)
            "$tanggal $jam"
        } catch (e: Exception) {
            dateTime
        }
    }
}