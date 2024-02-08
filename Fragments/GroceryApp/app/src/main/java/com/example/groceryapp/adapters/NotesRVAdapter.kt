package com.example.groceryapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.databinding.NoteLayoutBinding
import com.example.groceryapp.fragments.HomeFragmentDirections
import com.example.groceryapp.models.Notes

class NotesRVAdapter :RecyclerView.Adapter<NotesRVAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = differ.currentList[position]
        holder.itemBinding.noteTitle.text = currentNote.title
        holder.itemBinding.noteDesc.text = currentNote.description

        holder.itemView.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallBack = object: DiffUtil.ItemCallback<Notes>(){

        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.id==newItem.id && oldItem.title==newItem.title && oldItem.description==newItem.description
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem==newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)

    inner class ViewHolder(val itemBinding: NoteLayoutBinding):RecyclerView.ViewHolder(itemBinding.root)
}