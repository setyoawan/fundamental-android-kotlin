package com.example.awpsubmission3.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.awpsubmission3.data.storage.FavoriteUser
import com.example.awpsubmission3.data.storage.FavoriteUserDao
import com.example.awpsubmission3.data.storage.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application)  {
    private var userDao : FavoriteUserDao?
    private var userDb : UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> ? {
        return userDao?.getFavoriteUser()
    }
}