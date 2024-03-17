package com.example.todo_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.R
import com.example.todo_app.databinding.ViewTaskLinearLayoutBinding
import com.example.todo_app.models.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskRVViewModelAdapter(private val deleteUpdateCallback:(action:String, position:Int, task:Task)->Unit)
    :RecyclerView.Adapter<TaskRVViewModelAdapter.ViewHolder>() {

        private val taskList = arrayListOf<Task>()
    class ViewHolder(val viewTaskLinLayBinding: ViewTaskLinearLayoutBinding):RecyclerView.ViewHolder(viewTaskLinLayBinding.root)

    fun addAllTasks(newTaskList:List<Task>){
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRVViewModelAdapter.ViewHolder {
        return ViewHolder(ViewTaskLinearLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TaskRVViewModelAdapter.ViewHolder, position: Int) {
        val task = taskList[position]
        holder.viewTaskLinLayBinding.titleTxt.text = task.title
        holder.viewTaskLinLayBinding.descTxt.text = task.description
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss a", Locale.getDefault())
        holder.viewTaskLinLayBinding.dateTxt.text = dateFormat.format(task.date)
        holder.viewTaskLinLayBinding.delImg.setOnClickListener {
            if(holder.adapterPosition != -1){
                deleteUpdateCallback("delete",holder.adapterPosition,task)
            }
        }
        holder.viewTaskLinLayBinding.editImg.setOnClickListener {
            if(holder.adapterPosition!=-1){
                deleteUpdateCallback("update",holder.adapterPosition,task)
            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}