package com.example.pos.data.remote.model

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class TransaksiSingleResponse(
    @SerializedName("data") val data: Transaksi
) : Parcelable

