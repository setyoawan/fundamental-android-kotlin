package com.example.awpsubmission3.ui.main

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awpsubmission3.R
import com.example.awpsubmission3.data.User
import com.example.awpsubmission3.databinding.ActivityMainBinding
import com.example.awpsubmission3.ui.adapter.UserAdapter
import com.example.awpsubmission3.ui.detail.UserDetailActivity
import com.example.awpsubmission3.ui.favorite.FavoriteActivity
import com.example.awpsubmission3.utilities.SettingActivity
import com.example.awpsubmission3.utilities.SettingPreferences
import com.example.awpsubmission3.utilities.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.grey)
        }

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            com.example.awpsubmission3.utilities.MainViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

        showData()
        searchData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                Toast.makeText(this@MainActivity, "Tab favorit button" , Toast.LENGTH_SHORT)
                    .show()
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.action_settings -> {
                Toast.makeText(this@MainActivity, "Tab setting" , Toast.LENGTH_SHORT)
                    .show()
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    private fun showData(){
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.adapter = adapter
        }

        showLoading(true)
        viewModel.setUsers()
        viewModel.getUsers().observe(this@MainActivity, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemCLick(data: User) {
                Toast.makeText(this@MainActivity, "Tab " + data.login, Toast.LENGTH_SHORT)
                    .show()
                Intent(this@MainActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USER, data.login)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR_URL, data.avatar_url)
                    it.putExtra(UserDetailActivity.EXTRA_TYPE, data.type)
                    it.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })
    }

    private fun searchData(){
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemCLick(data: User) {
                Toast.makeText(this@MainActivity, "Tab " + data.login, Toast.LENGTH_SHORT).show()
                Intent(this@MainActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USER, data.login)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR_URL, data.avatar_url)
                    it.putExtra(UserDetailActivity.EXTRA_TYPE, data.type)
                    it.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        binding.apply {
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvUsers.layoutManager = GridLayoutManager(this@MainActivity, 2)

            } else {
                rvUsers.layoutManager = LinearLayoutManager(this@MainActivity,
                    LinearLayoutManager.HORIZONTAL, false)
                val dataset = arrayOfNulls<String>(50)
                    for (i in dataset.indices) {
                        dataset[i] = "item$i"
                    }
            }
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    showLoading(true)
                    viewModel.setSearchUsers(query)
                    viewModel.getSearchUsers().observe(this@MainActivity, {
                        if (it!=null) {
                            adapter.setList(it)
                            showLoading(false)
                        }
                    })
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}