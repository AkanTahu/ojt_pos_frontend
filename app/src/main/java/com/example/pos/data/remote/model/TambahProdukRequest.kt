package com.example.pos.data.remote.model

data class TambahProdukRequest(
    val nama: String,
    val harga_pokok: Int,
    val harga_jual: Int,
    val stok: String,
    val is_produk_stok: Boolean,
    val is_ganti_stok: Boolean,
    val gambar: String?,
    val keterangan: String?
)
