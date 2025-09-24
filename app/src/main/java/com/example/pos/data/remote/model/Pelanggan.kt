package com.example.pos.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pelanggan(
    val id: Int?,
    val nama: String?,
    val alamat: String?,
    val no_hp: String?,
    val barcode: String?,
    val keterangan: String?,
    val created_at: String?,
    val updated_at: String?
) : Parcelable
