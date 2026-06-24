package com.example.tugas11.network

import com.example.tugas11.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>
}
