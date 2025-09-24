package com.example.pos.data.remote

import com.example.pos.data.remote.model.LoginRequest
import com.example.pos.data.remote.model.LoginResponse
import com.example.pos.data.remote.model.PelangganResponse
import com.example.pos.data.remote.model.Produk
import com.example.pos.data.remote.model.ProdukResponse
import com.example.pos.data.remote.model.TambahProdukRequest
import com.example.pos.data.remote.model.TransaksiRequest
import com.example.pos.data.remote.model.TransaksiResponse
import com.example.pos.data.remote.model.TransaksiSingleResponse
import com.example.pos.data.remote.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): UserResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("logout")
    suspend fun logout(): retrofit2.Response<Unit>

    @GET("products")
    suspend fun getProducts(): ProdukResponse

    @GET("profile")
    suspend fun getUserMe(): UserResponse

    @GET("pelanggans")
    suspend fun getPelanggans(): PelangganResponse

    @GET("transaksis")
    suspend fun getTransaksis(): TransaksiResponse

    @POST("transaksis")
    suspend fun submitTransaksi(@Body request: TransaksiRequest): TransaksiSingleResponse

    @POST("products")
    suspend fun tambahProduk(@Body request: TambahProdukRequest): Produk

    @POST("register")
    suspend fun register(@Body body: Map<String, String>): LoginResponse
}
