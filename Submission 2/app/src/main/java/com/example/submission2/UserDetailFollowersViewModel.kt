package com.example.submission2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailFollowersViewModel : ViewModel() {

    val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(username: String){
        ApiConfig.apiInstance
            .getFollows(username, tipe="followers")
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                   if (response.isSuccessful) {
                       listFollowers.postValue(response.body())
                   }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })
    }

    fun getListFollowers() : LiveData<ArrayList<User>>{
        return listFollowers
    }

    companion object{
        private const val TAG = "FollowersViewModel"
    }


}