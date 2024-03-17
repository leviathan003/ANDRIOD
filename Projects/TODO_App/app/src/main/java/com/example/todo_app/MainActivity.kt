package com.example.todo_app

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.todo_app.databinding.ActivityMainBinding
import com.example.todo_app.utils.setupDialog

class MainActivity : AppCompatActivity() {
    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val addTaskDialog:Dialog by lazy {
        Dialog(this,R.style.DialogCustTheme).apply {
            setupDialog(R.layout.add_task_dialog)
        }
    }
    private val updateTaskDialog:Dialog by lazy {
        Dialog(this,R.style.DialogCustTheme).apply {
            setupDialog(R.layout.update_task_dialog)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        val addCloseBtn = addTaskDialog.findViewById<ImageView>(R.id.closeImg)
        val updateCloseBtn = updateTaskDialog.findViewById<ImageView>(R.id.closeImg)

        addCloseBtn.setOnClickListener {
            addTaskDialog.dismiss()
        }
        updateCloseBtn.setOnClickListener{
            updateTaskDialog.dismiss()
        }

        mainBinding.addTaskFABBtn.setOnClickListener {
            println("clicked")
            addTaskDialog.show()
        }
    }
}