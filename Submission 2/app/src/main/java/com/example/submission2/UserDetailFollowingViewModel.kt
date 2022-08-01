package com.example.submission2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailFollowingViewModel : ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setListFollowing(username: String){
        ApiConfig.apiInstance
            .getFollows(username, tipe="following")
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                   if (response.isSuccessful) {
                       listFollowing.postValue(response.body())
                   }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })
    }

    fun getListFollowing() : LiveData<ArrayList<User>>{
        return listFollowing
    }

    companion object{
        private const val TAG = "FollowingViewModel"
    }

}