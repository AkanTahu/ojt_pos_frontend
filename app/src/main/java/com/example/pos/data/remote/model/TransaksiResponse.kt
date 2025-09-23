package com.example.pos.data.remote.model

import com.google.gson.annotations.SerializedName
import com.example.pos.data.remote.model.Produk

data class TransaksiResponse(
    @SerializedName("data") val data: List<Transaksi>
)

data class Transaksi(
    val id: Int,
    val total_harga: Int,
    val laba: Int,
    val metode_pembayaran: String,
    val status_pembayaran: String,
    val created_at: String,
    val updated_at: String,
    val user: User,
    val pelanggan: Pelanggan,
    val detail_transaksi: List<DetailTransaksi>
)

data class User(
    val id: Int,
    val name: String
)

data class Pelanggan(
    val id: Int,
    val nama: String
)

data class DetailTransaksi(
    val id: Int,
    val qty: Int,
    val produk: Produk,
    val created_at: String,
    val updated_at: String
)
