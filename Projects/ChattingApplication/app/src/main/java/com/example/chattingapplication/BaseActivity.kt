package com.example.chattingapplication

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

open class BaseActivity: AppCompatActivity() {
    override fun onDestroy() {
        super.onDestroy()

        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserUid != null) {
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserUid)
            userRef.child("online").setValue(false)
        }
    }
}