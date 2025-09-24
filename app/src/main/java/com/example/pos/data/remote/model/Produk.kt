package com.example.pos.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Produk(
    val id: Int,
    val user_id: Int,
    val nama: String,
    val harga_pokok: Int,
    val harga_jual: Int,
    val stok: String,
    val is_produk_stok: Int,
    val is_ganti_stok: Int,
    val gambar: String?,
    val keterangan: String?,
    val created_at: String?,
    val updated_at: String?
) : Parcelable
