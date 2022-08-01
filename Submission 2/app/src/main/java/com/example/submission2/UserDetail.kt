package com.example.submission2

data class UserDetail(
    val login : String,
    val id : Int,
    val avatar_url : String,
    val followers_url : String,
    val following_url : String,
    val name : String,
    val company : String,
    val location : String,
    val blog : String,
    val followers : Int,
    val following : Int
)
