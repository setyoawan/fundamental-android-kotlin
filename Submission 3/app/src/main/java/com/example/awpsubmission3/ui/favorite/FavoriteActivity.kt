package com.example.awpsubmission3.ui.favorite

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awpsubmission3.R
import com.example.awpsubmission3.data.User
import com.example.awpsubmission3.data.storage.FavoriteUser
import com.example.awpsubmission3.databinding.ActivityFavoriteBinding
import com.example.awpsubmission3.ui.adapter.UserAdapterVertical
import com.example.awpsubmission3.ui.detail.UserDetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter : UserAdapterVertical
    private lateinit var viewModel : FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.grey)
        }

        val actionbar = supportActionBar
        actionbar!!.title = "Favorite user"
        actionbar.setDisplayHomeAsUpEnabled(true)

        showData()
    }

    private fun showData() {
        adapter = UserAdapterVertical()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserAdapterVertical.OnItemClickCallback {
            override fun onItemCLick(data: User) {
                Toast.makeText(this@FavoriteActivity, "Tab " + data.login, Toast.LENGTH_SHORT)
                    .show()
                Intent(this@FavoriteActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USER, data.login)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR_URL, data.avatar_url)
                    it.putExtra(UserDetailActivity.EXTRA_TYPE, data.type)
                    it.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUsers.adapter = adapter
        }

        showPictFav(true)
        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
                showPictFav(false)
            }
        })
    }

    private fun mapList(users : List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.login,
                user.avatar_url,
                user.type,
                user.id
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showPictFav(state: Boolean) {
        if (state) {
            binding.ivFavoriteImage.visibility = View.VISIBLE
        } else {
            binding.ivFavoriteImage.visibility = View.GONE
        }
    }
}