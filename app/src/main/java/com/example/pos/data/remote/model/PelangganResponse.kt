package com.example.pos.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PelangganResponse(
    val data: List<Pelanggan>
) : Parcelable

