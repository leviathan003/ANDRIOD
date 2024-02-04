package com.example.emailpass_auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.emailpass_auth.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateNotesActivity : AppCompatActivity() {

    private lateinit var title:EditText
    private lateinit var desc:EditText
    private lateinit var saveBtn: Button
    private lateinit var db: DatabaseReference
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_notes)

        title = findViewById(R.id.titleEntry)
        desc = findViewById(R.id.descEntry)
        saveBtn = findViewById(R.id.saveButton)
        db = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        saveBtn.setOnClickListener {
            //Error checking for title and description here
            val currentUser = auth.currentUser
            currentUser?.let {
                user->
                val notekey = db.child("users").child(user.uid).child("notes").push().key
                val note = Notes(title.text.toString(),desc.text.toString(),notekey?:"")

                if(notekey!=null){
                    db.child("users").child(user.uid).child("notes").child(notekey).setValue(note)
                        .addOnCompleteListener {
                            task->
                            if(task.isSuccessful){
                                Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show()
                                finish()
                            }else{
                                Toast.makeText(this,"Note not Saved",Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }
}