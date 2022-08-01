package com.example.submission2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("users")
    @Headers("Authorization: token ")
    fun getUsers(): Call<UserResponse>

    @GET("search/users")
    @Headers("Authorization: token ")
    fun getSearchUsers(
        @Query("q") query : String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ")
    fun getUserDetail(
        @Path("username") username : String
    ): Call<UserDetail>

    @GET("users/{username}/{tipe}")
    @Headers("Authorization: token ")
    fun getFollows(
        @Path("username") username : String,
        @Path("tipe") tipe: String
    ): Call<ArrayList<User>>

}