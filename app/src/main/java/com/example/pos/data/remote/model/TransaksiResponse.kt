package com.example.pos.data.remote.model

import com.google.gson.annotations.SerializedName
import com.example.pos.data.remote.model.Produk
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class TransaksiResponse(
    @SerializedName("data") val data: @RawValue List<Transaksi>
) : Parcelable

@Parcelize
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
    val detail_transaksi: @RawValue List<DetailTransaksi>
) : Parcelable

@Parcelize
data class User(
    val id: Int?,
    val name: String?,
    val is_admin: Boolean?,
    val email: String?,
    val email_verified_at: String?,
    val created_at: String?,
    val updated_at: String?,
    val toko_id: Int?
) : Parcelable

@Parcelize
data class DetailTransaksi(
    val id: Int,
    val qty: Int,
    val produk: @RawValue Produk,
    val created_at: String,
    val updated_at: String
) : Parcelable