package com.example.awpsubmission3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.awpsubmission3.data.User
import com.example.awpsubmission3.databinding.ItemUserBinding

class UserAdapterVertical : RecyclerView.Adapter<UserAdapterVertical.UserViewHolder>() {
    private var list = ArrayList<User>()

    private var onItemClickCallback : UserAdapterVertical.OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: UserAdapterVertical.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users : ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }
    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemCLick(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(ivItemAvatar)
                tvItemName.text = user.login
                tvItemType.text = user.type
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemCLick(data: User)
    }
}