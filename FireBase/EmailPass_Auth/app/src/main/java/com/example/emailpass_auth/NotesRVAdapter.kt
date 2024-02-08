package com.example.emailpass_auth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emailpass_auth.Notes

class NotesRVAdapter(
    private val context:Context,
    private var notes:MutableList<Notes>,
    private val updtClickedListener: updateBtnClickedListener,
    private val dltBtnClickedListener: deleteBtnClickedListener): RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {

    interface updateBtnClickedListener{
        fun onUpdateButtonClicked(id:String,pos:Int)
    }

    interface deleteBtnClickedListener{
        fun onDeleteButtonClicked(id:String,pos:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notes_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note,position)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateData(newNotesList: MutableList<Notes>) {
        notes = newNotesList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemView:View):RecyclerView.ViewHolder(itemView){

        private val title:TextView = itemView.findViewById(R.id.titleDisp)
        private val desc:TextView = itemView.findViewById(R.id.descDisp)
        private val updtBtn:Button = itemView.findViewById(R.id.updateBtn)
        private val dltBtn:Button = itemView.findViewById(R.id.deleteBtn)

        fun bind(note:Notes,pos:Int){
            title.text = note.title
            desc.text = note.desc

            updtBtn.setOnClickListener {
                updtClickedListener.onUpdateButtonClicked(note.noteID,pos)
            }
            dltBtn.setOnClickListener {
                dltBtnClickedListener.onDeleteButtonClicked(note.noteID,pos)
            }

        }

    }

}