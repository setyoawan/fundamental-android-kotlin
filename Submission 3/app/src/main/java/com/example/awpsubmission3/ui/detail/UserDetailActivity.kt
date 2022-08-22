package com.example.awpsubmission3.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.awpsubmission3.R
import com.example.awpsubmission3.databinding.ActivityUserDetailBinding
import com.example.awpsubmission3.ui.adapter.UserDetailPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {
    private lateinit var username: String
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.grey)
        }

        supportActionBar?.hide()

       /* val actionbar = supportActionBar
        actionbar!!.title = "Detail user"
        actionbar.setDisplayHomeAsUpEnabled(true)*/

        binding.backButton.setOnClickListener {
            onSupportNavigateUp()
        }

        val username = intent.getStringExtra(EXTRA_USER)
        val avatar_url = intent.getStringExtra(EXTRA_AVATAR_URL)
        val type = intent.getStringExtra(EXTRA_TYPE)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        val bundle = Bundle()
        bundle.putString(EXTRA_USER, username)

        viewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)


        if (username != null) {
            viewModel.setUserDetail(username)
        }
        viewModel.getUserDetail().observe(this, {
            if (it !=null) {
                binding.apply {
                    Glide.with(this@UserDetailActivity)
                        .load(it.avatar_url)
                        .circleCrop()
                        .into(ivAvatarDetail)
                    tvNameDetail.text = it.name
                    tvCompanyDetail.text = it.company
                    tvLocationDetail.text = it.location
                    tvFollowersDetail.text = "${it.followers} followers"
                    tvFollowingDetail.text = "${it.following} following"
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                if (username != null && avatar_url != null && type !=null) {
                    viewModel.addToFavorite(username, avatar_url, type,  id)
                }
                Toast.makeText(this, "$username Add to favorite", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.removeFromFavorite(id)
                Toast.makeText(this, "$username Remove from favorite", Toast.LENGTH_SHORT).show()
            }
            binding.toggleFavorite.isChecked =  _isChecked
        }

        val sectionsPagerAdapter = UserDetailPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
        supportActionBar?.elevation = 0f
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object{
        const val EXTRA_USER = "extra_user"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
        const val EXTRA_TYPE = "extra_type"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab1,
            R.string.tab2
        )
    }
}