package com.example.awpsubmission3.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.awpsubmission3.api.ApiConfig
import com.example.awpsubmission3.data.UserDetail
import com.example.awpsubmission3.data.storage.FavoriteUser
import com.example.awpsubmission3.data.storage.FavoriteUserDao
import com.example.awpsubmission3.data.storage.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<UserDetail>()

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        ApiConfig.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<UserDetail> {
                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    Log.e(UserDetailViewModel.TAG, "onFailure: ${t.message.toString()}")
                }

            })
    }

    fun getUserDetail(): LiveData<UserDetail> {
        return user
    }

    fun addToFavorite(username: String, avatar_url : String, type : String, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                avatar_url,
                type,
                id
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

    companion object {
        private const val TAG = "UserDetailViewModel"
    }
}