package com.example.submission1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class UserDetail : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.grey)
        }

        val dataUser = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val imgAvatar = findViewById<ImageView>(R.id.iv_item_avatar_detail)
        Glide.with(this)
            .load(dataUser.avatar)
            .circleCrop()
            .into(imgAvatar)
        findViewById<TextView>(R.id.tv_item_name_detail).text = (dataUser.name)
        val objectDetail = "Company : ${dataUser.company.toString()}\nUsername : ${dataUser.username}\nLocation : ${dataUser.location}\nRepository: ${dataUser.repository}\n" +
                "Followers: ${dataUser.followers}\n" +
                "Following: ${dataUser.following}"
        findViewById<TextView>(R.id.tv_item_object_detail).text = objectDetail
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}