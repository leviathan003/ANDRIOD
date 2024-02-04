package com.example.emailpass_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emailpass_auth.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OpenNotesActivity : AppCompatActivity() {

    private lateinit var noteView:RecyclerView
    private lateinit var db:DatabaseReference
    private lateinit var auth:FirebaseAuth
    private lateinit var adapter:NotesRVAdapter
    private val noteList = mutableListOf<Notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_notes)

        noteView = findViewById(R.id.notesRV)
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().reference
        adapter = NotesRVAdapter(this, mutableListOf(),object: NotesRVAdapter.updateBtnClickedListener{
            override fun onUpdateButtonClicked(id:String,pos:Int) {
                startActivity(Intent(this@OpenNotesActivity,UpdateNoteActivity::class.java).putExtra("id",id))
                noteList.clear()
            }
        },object: NotesRVAdapter.deleteBtnClickedListener{
            override fun onDeleteButtonClicked(id:String,pos:Int) {
                val currentUser = auth.currentUser
                currentUser?.let {
                    user->
                    val noteRef = db.child("users").child(user.uid).child("notes")
                        .child(id).removeValue()
                    noteList.clear()
                }
                Toast.makeText(this@OpenNotesActivity,"Note Deleted",Toast.LENGTH_SHORT).show()
            }
        })
        noteView.adapter = adapter
        noteView.setHasFixedSize(true)
        noteView.layoutManager = LinearLayoutManager(this)

        val currentUser = auth.currentUser
        currentUser?.let{
            user->
            val noteRef = db.child("users").child(user.uid).child("notes")
            noteRef.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(noteSnapshot in snapshot.children){
                        val note = noteSnapshot.getValue(Notes::class.java)
                        note?.let {
                            noteList.add(it)
                        }
                    }
                    noteList.reverse()
                    updateData(noteList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

    }

    private fun updateData(noteList: MutableList<Notes>) {
        adapter.updateData(noteList)
    }
}