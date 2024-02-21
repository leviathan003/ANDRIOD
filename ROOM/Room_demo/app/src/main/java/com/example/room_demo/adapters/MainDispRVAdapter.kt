package com.example.room_demo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room_demo.R
import com.example.room_demo.database.UsersEntity

class MainDispRVAdapter(private val onDeleteClickListener: (UsersEntity) -> Unit,
                        private val onItemClick: (UsersEntity) -> Unit) :
    ListAdapter<UsersEntity, MainDispRVAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.disp_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = getItem(position)
        holder.bind(currentUser)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameEmailDisp: TextView = itemView.findViewById(R.id.nameEmailDisp)
        private val deleteBtn: Button = itemView.findViewById(R.id.deleteBtn)

        fun bind(user: UsersEntity) {
            nameEmailDisp.text = "${user.name} | ${user.email}"
            deleteBtn.setOnClickListener {
                onDeleteClickListener.invoke(user)
            }
            itemView.setOnClickListener {
                onItemClick.invoke(user)
            }
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<UsersEntity>() {
    override fun areItemsTheSame(oldItem: UsersEntity, newItem: UsersEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UsersEntity, newItem: UsersEntity): Boolean {
        return oldItem == newItem
    }
}
