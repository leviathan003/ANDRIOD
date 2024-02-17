package com.example.chattingapplication.chat

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chattingapplication.BaseActivity
import com.example.chattingapplication.R
import com.example.chattingapplication.adapters.MessagesRVAdapter
import com.example.chattingapplication.databinding.ActivityChatBinding
import com.example.chattingapplication.models.Message
import com.example.chattingapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : BaseActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var db:DatabaseReference
    private lateinit var userData:User
    private val messageList = mutableListOf<Message>()
    private lateinit var adapter:MessagesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.textPrimary )

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().reference
        userData = intent.getSerializableExtra("user") as User

        binding.recieverName.text = userData.name
        if (userData.isOnline){
            binding.recieverStatus.text = "Online"
        }else{
            binding.recieverStatus.text = "Offline"
        }

        val currentUser = auth.currentUser

        binding.sendBtn.setOnClickListener{
            val message = binding.messageEntry.text.toString()
            if(message.isNotBlank()){
                messageList.clear()
                if (currentUser != null) {
                    val currentTime = System.currentTimeMillis()
                    val dataMessage = Message(currentUser.uid, userData.uid, message, currentTime)
                    db.child("messages").push().setValue(dataMessage)
                }
                binding.messageEntry.text.clear()
            }
        }

        adapter = MessagesRVAdapter(this, mutableListOf(),auth)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val data = dataSnapshot.getValue(Message::class.java)
                    data?.let {
                        if ((it.senderUID == currentUser?.uid && it.recieverUID == userData.uid) ||
                            (it.senderUID == userData.uid && it.recieverUID == currentUser?.uid)) {
                            messageList.add(it)
                        }
                    }
                }
                messageList.sortBy { it.timestamp }
                adapter.setData(messageList)
                binding.messageRV.post {
                    binding.messageRV.scrollToPosition(adapter.itemCount - 1)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("DB ERROR",error.message)
            }
        }

        db.child("messages").addValueEventListener(valueEventListener)

        binding.messageRV.adapter = adapter
        binding.messageRV.layoutManager = LinearLayoutManager(this)
        binding.messageRV.setHasFixedSize(true)

    }
}