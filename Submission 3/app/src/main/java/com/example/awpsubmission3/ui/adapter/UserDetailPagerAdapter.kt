package com.example.awpsubmission3.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.awpsubmission3.ui.detail.UserDetailFollowersFragment
import com.example.awpsubmission3.ui.detail.UserDetailFollowingFragment

class UserDetailPagerAdapter(activity: AppCompatActivity, data: Bundle) : FragmentStateAdapter(activity){

    private var fragmentBundle : Bundle
    init {
        fragmentBundle = data
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = UserDetailFollowersFragment()
            1 -> fragment = UserDetailFollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

}