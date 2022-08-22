package com.example.awpsubmission3.data.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val login: String,
    val avatar_url : String,
    val type : String,
    @PrimaryKey
    val id: Int
)

