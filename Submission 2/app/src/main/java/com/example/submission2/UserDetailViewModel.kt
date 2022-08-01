package com.example.submission2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel: ViewModel() {

    val user = MutableLiveData<UserDetail>()

    fun setUserDetail(username: String) {
        ApiConfig.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<UserDetail>{
                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    Log.e(UserDetailViewModel.TAG, "onFailure: ${t.message.toString()}")
                }

            })
    }

    fun getUserDetail() : LiveData<UserDetail> {
        return user
    }

    companion object{
        private const val TAG = "UserDetailViewModel"
    }
}