package com.example.pos.data.remote.model

// Parcelable data class untuk produk dan qty
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ProdukQty(val produk: @RawValue Produk, val qty: Int) : Parcelable
