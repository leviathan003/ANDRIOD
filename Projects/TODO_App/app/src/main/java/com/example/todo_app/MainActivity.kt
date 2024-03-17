package com.example.todo_app

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.query
import com.example.todo_app.adapters.TaskRVAdapter
import com.example.todo_app.databinding.ActivityMainBinding
import com.example.todo_app.models.Task
import com.example.todo_app.utils.Status
import com.example.todo_app.utils.longToastShow
import com.example.todo_app.utils.setupDialog
import com.example.todo_app.viewmodels.TaskViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

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

    private val taskViewModel:TaskViewModel by lazy {
        ViewModelProvider(this)[TaskViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        val titleText = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskTitle)
        val descText = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskDesc)
        val addCloseBtn = addTaskDialog.findViewById<ImageView>(R.id.closeImg)
        val updateCloseBtn = updateTaskDialog.findViewById<ImageView>(R.id.closeImg)
        val saveTaskBtn = addTaskDialog.findViewById<Button>(R.id.saveTaskBtn)
        val updateTaskBtn = updateTaskDialog.findViewById<Button>(R.id.updateTaskBtn)
        val updateTitle = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskTitle)
        val updateDesc = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskDesc)

        addCloseBtn.setOnClickListener {
            addTaskDialog.dismiss()
        }
        updateCloseBtn.setOnClickListener{
            updateTaskDialog.dismiss()
        }
        saveTaskBtn.setOnClickListener {
            addTaskDialog.dismiss()
            val newTask = Task(
                UUID.randomUUID().toString(),
                titleText.text.toString().trim(),
                descText.text.toString().trim(),
                Date()
            )
            taskViewModel.insertTask(newTask).observe(this){
                when(it.status){
                    Status.LOADING->{
                        //
                    }
                    Status.ERROR->{
                        it.message?.let { message -> longToastShow(message) }
                    }
                    Status.SUCCESS->{
                        if(it.data?.toInt()!=-1){
                            longToastShow("Task added Successfully")
                        }
                    }
                }
            }
        }
        mainBinding.addTaskFABBtn.setOnClickListener {
            titleText.setText("")
            descText.setText("")
            addTaskDialog.show()
        }

        val taskRecyclerViewAdapter = TaskRVAdapter(){action,position, task ->
                if(action=="delete") {
                    taskViewModel.deleteTasks(task).observe(this) {
                        when (it.status) {
                            Status.LOADING -> {
                                //
                            }
                            Status.ERROR -> {
                                it.message?.let { message -> longToastShow(message) }
                            }

                            Status.SUCCESS -> {
                                if (it.data != -1) {
                                    longToastShow("Task deleted Successfully")
                                }
                            }
                        }
                    }
                }
                else if(action=="update"){
                    updateTaskDialog.show()
                    updateTitle.setText(task.title)
                    updateDesc.setText(task.description)
                    updateTaskBtn.setOnClickListener {
                        val updatedTask = Task(
                            task.id,
                            updateTitle.text.toString().trim(),
                            updateDesc.text.toString().trim(),
                            Date()
                        )
                        taskViewModel.updateTask(updatedTask).observe(this){
                            when(it.status){
                                Status.LOADING->{
                                    //
                                }
                                Status.ERROR->{
                                    it.message?.let { message -> longToastShow(message) }
                                }
                                Status.SUCCESS->{
                                    if(it.data!=-1){
                                        longToastShow("Task updated Successfully")
                                    }
                                }
                            }
                        }
                        updateTaskDialog.dismiss()
                    }
                }
            }

            mainBinding.taskRV.adapter = taskRecyclerViewAdapter
            taskRecyclerViewAdapter.registerAdapterDataObserver(object :RecyclerView.AdapterDataObserver(){
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    mainBinding.taskRV.smoothScrollToPosition(positionStart)
                }
            })
            callSearch(taskRecyclerViewAdapter)
            callGetTaskList(taskRecyclerViewAdapter)
        }

    private fun callSearch(taskRecyclerViewAdapter:TaskRVAdapter) {
        mainBinding.edTaskTitle.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(query: Editable?) {
                if(query.toString().isNotEmpty()){
                    CoroutineScope(Dispatchers.Main).launch {
                        taskViewModel.searchTaskList(query.toString()).collect{
                            when(it.status){
                                Status.LOADING->{
                                    //
                                }
                                Status.ERROR->{
                                    it.message?.let { message -> longToastShow(message) }
                                }
                                Status.SUCCESS-> {
                                    it.data?.collect{taskList->
                                        taskRecyclerViewAdapter.submitList(taskList)
                                    }
                                }
                            }
                        }
                    }
                }else{
                    callGetTaskList(taskRecyclerViewAdapter)
                }
            }
        })
    }

    private fun callGetTaskList(taskRecyclerViewAdapter:TaskRVAdapter){
        CoroutineScope(Dispatchers.Main).launch {
            taskViewModel.getTasks().collect{
                when(it.status){
                    Status.LOADING->{
                        //
                    }
                    Status.ERROR->{
                        it.message?.let { message -> longToastShow(message) }
                    }
                    Status.SUCCESS-> {
                        it.data?.collect{taskList->
                            taskRecyclerViewAdapter.submitList(taskList)
                        }
                    }
                }
            }
        }
    }
}