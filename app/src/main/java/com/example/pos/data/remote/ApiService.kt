package com.example.pos.data.remote

import com.example.pos.data.remote.model.LoginRequest
import com.example.pos.data.remote.model.LoginResponse
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
    suspend fun logout(@Header("Authorization") token: String): retrofit2.Response<Unit>
}
