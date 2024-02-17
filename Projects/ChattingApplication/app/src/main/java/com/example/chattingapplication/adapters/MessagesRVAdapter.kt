package com.example.chattingapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.chattingapplication.R
import com.example.chattingapplication.models.Message
import com.google.firebase.auth.FirebaseAuth

class MessagesRVAdapter(var context:Context,var messageList: MutableList<Message>,var auth:FirebaseAuth):RecyclerView.Adapter<MessagesRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageList[position]
        holder.bind(message,position)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun setData(messageList: MutableList<Message>) {
        this.messageList = messageList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private var messageDisp = itemView.findViewById<TextView>(R.id.messageDisp)
        private var layout = itemView.findViewById<LinearLayout>(R.id.messageDispLay)
        fun bind(message: Message, position: Int) {
            if(message.senderUID == auth.currentUser?.uid){
                layout.gravity = GravityCompat.END
            }else{
                layout.gravity = GravityCompat.START
            }
            messageDisp.text = message.message
        }
    }
}