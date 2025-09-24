package com.example.pos.data.remote.repository

import com.example.pos.data.remote.ApiService
import com.example.pos.data.remote.model.TransaksiRequest
import com.example.pos.data.remote.model.TransaksiResponse
import com.example.pos.data.remote.model.TransaksiSingleResponse

class TransaksiRepository(private val apiService: ApiService) {
    suspend fun submitTransaksi(request: TransaksiRequest): TransaksiSingleResponse? {
        return try {
            apiService.submitTransaksi(request)
        } catch (e: Exception) {
            null
        }
    }
}

