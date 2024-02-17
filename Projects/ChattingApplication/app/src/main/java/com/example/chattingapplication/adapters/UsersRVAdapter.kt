package com.example.chattingapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.chattingapplication.R
import com.example.chattingapplication.models.Message
import com.example.chattingapplication.models.User
import com.google.firebase.auth.FirebaseAuth

class UsersRVAdapter(private val context: Context,private var users:MutableList<User>,
                    private val onClickedListener: onClickListener):RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    private var auth = FirebaseAuth.getInstance()

    interface onClickListener{
        fun onClickedItem(item:User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.profile_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user,position)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setData(usersList: MutableList<User>) {
        this.users = usersList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        private val userName:TextView = itemView.findViewById(R.id.usernameDisp)
        private val statusOnline:TextView = itemView.findViewById(R.id.onlineStatusDisp)
        private val profileCard:CardView = itemView.findViewById(R.id.profileCard)
        fun bind(user: User, position: Int) {
            userName.text = user.name
                if(!user.isOnline){
                    statusOnline.visibility = View.INVISIBLE
                }
            profileCard.setOnClickListener {
                onClickedListener.onClickedItem(user)
            }
        }
    }

}