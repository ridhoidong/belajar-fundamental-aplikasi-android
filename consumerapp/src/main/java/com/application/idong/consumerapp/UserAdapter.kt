package com.application.idong.consumerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.idong.aplikasigithubuser3.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * Created by ridhopratama on 05,October,2020
 */

class UserAdapter() : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var users = mutableListOf<User>()
    var actionUserListener: ActionUserListener? = null

    fun updateData(newUser: MutableList<User>) {
        users.clear()
        users.addAll(newUser)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user, actionUserListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User, actionUserListener: ActionUserListener?) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user?.avatar_url)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .override(150, 150)
                    .into(ivImage)
                tvUsername.text = user.login
                tvUrl.text = user.html_url
                this.setOnClickListener {
                    actionUserListener?.onSelectUser(user)
                }
            }
        }
    }
}
