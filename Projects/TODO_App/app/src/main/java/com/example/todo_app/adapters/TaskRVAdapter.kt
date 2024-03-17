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
import com.example.todo_app.models.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskRVAdapter(private val deleteUpdateCallback:(action:String,position:Int,task:Task)->Unit)
    :ListAdapter<Task,TaskRVAdapter.ViewHolder>(DiffCallBack()) {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var titleText:TextView = itemView.findViewById(R.id.titleTxt)
        var descText:TextView = itemView.findViewById(R.id.descTxt)
        var date:TextView = itemView.findViewById(R.id.dateTxt)
        val deleteImg:ImageView = itemView.findViewById(R.id.delImg)
        val updateImg:ImageView = itemView.findViewById(R.id.editImg)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRVAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_task_linear_layout,parent,false))
    }

    override fun onBindViewHolder(holder: TaskRVAdapter.ViewHolder, position: Int) {
        val task = getItem(position)
        holder.titleText.text = task.title
        holder.descText.text = task.description
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss a", Locale.getDefault())
        holder.date.text = dateFormat.format(task.date)
        holder.deleteImg.setOnClickListener {
            if(holder.adapterPosition != -1){
                deleteUpdateCallback("delete",holder.adapterPosition,task)
            }
        }
        holder.updateImg.setOnClickListener {
            if(holder.adapterPosition!=-1){
                deleteUpdateCallback("update",holder.adapterPosition,task)
            }
        }
    }

    class DiffCallBack:DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem==newItem
        }

    }
}