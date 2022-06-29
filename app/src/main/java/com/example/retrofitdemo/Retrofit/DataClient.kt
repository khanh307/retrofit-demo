package com.example.retrofitdemo.Retrofit

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface DataClient {
    @Multipart
    @POST("upload.php")
    fun  uploadImage(@Part photo: MultipartBody.Part ): Call<String>

    @FormUrlEncoded
    @POST("insert.php")
    fun insertData(@Field("name") name: String,
                    @Field("image") image: String): Call<String>
}