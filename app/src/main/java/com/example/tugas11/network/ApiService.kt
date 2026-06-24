package com.example.tugas11.network

import com.example.tugas11.model.Photo
import retrofit2.http.GET

interface ApiService {

    @GET("photos")
    suspend fun getPhotos(): List<Photo>
}
