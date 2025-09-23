package com.example.pos.data.remote

import com.example.pos.data.remote.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): UserResponse

    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}