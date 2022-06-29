package com.example.retrofitdemo.Retrofit

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface DataClient {
    @Multipart
    @POST("upload.php")
    fun  uploadImage(@Part photo: MultipartBody.Part ): Call<String>
}