package com.example.pos.data.remote.model

data class TransaksiRequest(
    val pelanggan_id: Int? = null,
    val metode_pembayaran: String,
    val status_pembayaran: String = "lunas",
    val items: List<ItemTransaksi>,
    val keterangan: String? = null
)

data class ItemTransaksi(
    val produk_id: Int,
    val qty: Int
)

