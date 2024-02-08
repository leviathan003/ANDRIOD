package com.example.groceryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.groceryapp.MainActivity
import com.example.groceryapp.R
import com.example.groceryapp.databinding.FragmentEditNoteBinding
import com.example.groceryapp.models.Notes
import com.example.groceryapp.viewmodels.NoteViewModel

class EditNoteFragment : Fragment(R.layout.fragment_edit_note),MenuProvider {

    private var editNoteBinding: FragmentEditNoteBinding? =null
    private val binding get() = editNoteBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var currNote: Notes

    private val args:EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).viewModel
        currNote = args.note!!

        binding.editNoteTitle.setText(currNote.title)
        binding.editNoteDesc.setText(currNote.description)

        binding.editNoteFab.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()

            if(noteTitle.isNotEmpty()){
                val note = Notes(currNote.id,noteTitle,noteDesc)
                noteViewModel.updtNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }
            else{
                Toast.makeText(context,"Enter Note Title",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun delNote(){
        AlertDialog.Builder(activity as MainActivity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete note?")
            setPositiveButton("Delete"){
                _,_->
                noteViewModel.delNote(currNote)
                Toast.makeText(context,"Note Deleted!!",Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu->{
                delNote()
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding=null
    }
}