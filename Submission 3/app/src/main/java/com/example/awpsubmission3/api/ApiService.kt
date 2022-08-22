package com.example.awpsubmission3.api

import com.example.awpsubmission3.BuildConfig
import com.example.awpsubmission3.data.User
import com.example.awpsubmission3.data.UserDetail
import com.example.awpsubmission3.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: ${BuildConfig.API_TOKEN}")
    @GET("users")
    fun getUsers(): Call<ArrayList<User>>

    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query : String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username : String
    ): Call<UserDetail>

    @GET("users/{username}/{tipe}")
    fun getFollows(
        @Path("username") username : String,
        @Path("tipe") tipe: String
    ): Call<ArrayList<User>>
}