package com.example.chattingapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chattingapplication.adapters.UsersRVAdapter
import com.example.chattingapplication.databinding.ActivityHomeBinding
import com.example.chattingapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db:DatabaseReference
    private var userList = mutableListOf<User>()
    private lateinit var adapter:UsersRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.textPrimary )

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().reference

        setSupportActionBar(binding.homeToolbar)

        adapter = UsersRVAdapter(this, userList,object:UsersRVAdapter.onClickListener {
            override fun onClickedItem(item: User) {
                startActivity(Intent(this@HomeActivity, ChatActivity::class.java).putExtra("user", item))
            }
        })

        val currUser = auth.currentUser
        db.child("users").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(currSnapshot in snapshot.children){
                    if(currUser!!.uid != currSnapshot.child("uid").value && currUser!=null){
                        userList.add(User(currSnapshot.child("uid").value.toString(),currSnapshot.child("name").value.toString()
                               ,currSnapshot.child("online").value.toString().toBoolean()))
                    }
                }
                adapter.setData(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RDB ERROR",error.message)
            }
        })

//        currentUser?.let {
//            val userRef = db.child("users")
//            userRef.get().addOnCompleteListener {
//                if(it.isSuccessful){
//                    val dataSnapshot = it.result
//                    for(data in dataSnapshot.children){
//                        if(data.child("uid").value.toString() != auth.currentUser?.uid){
//                            userList.add(User(data.child("uid").value.toString(),data.child("name").value.toString()
//                                ,data.child("online").value.toString().toBoolean()))
//                        }
//                    }
//                    adapter = UsersRVAdapter(this,userList,object:UsersRVAdapter.onClickListener{
//                        override fun onClickedItem(item: User) {
//                            startActivity(Intent(this@HomeActivity, ChatActivity::class.java).putExtra("user",item))
//                            userList.clear()
//                        }
//                    })
//                    binding.contactsRV.adapter = adapter
//                    binding.contactsRV.layoutManager = LinearLayoutManager(this)
//                    binding.contactsRV.setHasFixedSize(true)
//
//                }else{
//                    Toast.makeText(this,"Something went wrong!!",Toast.LENGTH_SHORT).show()
//                }
//            }
//        }

        binding.contactsRV.adapter = adapter
        binding.contactsRV.layoutManager = LinearLayoutManager(this)
        binding.contactsRV.setHasFixedSize(true)

        binding.logoutBtn.setOnClickListener {
            db.child("users").child(auth.currentUser!!.uid).child("online").setValue(false)
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        db.child("users").child(auth.currentUser!!.uid).child("online").setValue(false)
    }
}