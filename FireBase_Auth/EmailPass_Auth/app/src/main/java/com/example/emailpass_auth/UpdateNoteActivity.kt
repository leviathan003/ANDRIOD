package com.example.emailpass_auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var titleUpdated:EditText
    private lateinit var descUpdated:EditText
    private lateinit var updateBtn:Button
    private lateinit var auth:FirebaseAuth
    private lateinit var db:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        titleUpdated = findViewById(R.id.updtTitleEntry)
        descUpdated = findViewById(R.id.updtDescEntry)
        updateBtn = findViewById(R.id.updateButton)
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().reference

        var id = intent.getSerializableExtra("id").toString()

        updateBtn.setOnClickListener {
            //Error checking here

            val currentUser = auth.currentUser
            currentUser?.let {
                user->
                val noteRef = db.child("users").child(user.uid).child("notes")
                val updatedNote = Notes(titleUpdated.text.toString(),descUpdated.text.toString(),id)
                noteRef.child(id).setValue(updatedNote)
                    .addOnCompleteListener{
                        task->
                        if (task.isSuccessful){
                            Toast.makeText(this,"Note Updated",Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Note Update Failed!!",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }


}