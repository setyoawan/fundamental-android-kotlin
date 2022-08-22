package com.example.awpsubmission3.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awpsubmission3.R
import com.example.awpsubmission3.data.User
import com.example.awpsubmission3.databinding.FragmentFfBinding
import com.example.awpsubmission3.ui.adapter.UserAdapterVertical


class UserDetailFollowersFragment: Fragment(R.layout.fragment_ff) {
    private var _binding : FragmentFfBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserDetailFollowersViewModel
    private lateinit var adapter: UserAdapterVertical
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(UserDetailActivity.EXTRA_USER).toString()
        _binding = FragmentFfBinding.bind(view)
        followers()
    }

    private fun followers(){
        adapter = UserAdapterVertical()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailFollowersViewModel::class.java)
        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = adapter
        }

        showLoading(true)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner,{
            if (it!=null) {
                adapter.setList(it)
                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : UserAdapterVertical.OnItemClickCallback{
            override fun onItemCLick(data: User) {
                Toast.makeText(context, "Tab " + data.login, Toast.LENGTH_SHORT).show()
                Intent(context, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USER, data.login)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR_URL, data.avatar_url)
                    it.putExtra(UserDetailActivity.EXTRA_TYPE, data.type)
                    it.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}