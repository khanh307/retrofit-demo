package com.example.retrofitdemo.Retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private lateinit var retrofit: Retrofit
    public fun getClient(baseURL: String): Retrofit{
        var builder = OkHttpClient.Builder()
             .retryOnConnectionFailure(true)
            .build()
        var gson = GsonBuilder().setLenient().create()
        retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(builder)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit
    }

}