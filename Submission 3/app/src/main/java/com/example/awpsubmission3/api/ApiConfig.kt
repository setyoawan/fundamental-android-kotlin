package com.example.awpsubmission3.api

import com.example.awpsubmission3.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private val BASE_URL = BuildConfig.BASE_URL
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiInstance = retrofit.create(ApiService::class.java)
}