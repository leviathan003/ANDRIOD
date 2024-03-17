package com.example.todo_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.databinding.ViewTaskGridLayoutBinding
import com.example.todo_app.databinding.ViewTaskLinearLayoutBinding
import com.example.todo_app.models.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskRVViewBindingAdapter(private val isList:MutableLiveData<Boolean>,
    private val deleteUpdateCallback:(action:String, position:Int, task:Task)->Unit)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val taskList = arrayListOf<Task>()
    class LinearLayViewHolder(private val viewTaskLinLayBinding: ViewTaskLinearLayoutBinding):RecyclerView.ViewHolder(viewTaskLinLayBinding.root)
    {
        fun bind(task:Task,deleteUpdateCallback:(action:String, position:Int, task:Task)->Unit){
            viewTaskLinLayBinding.titleTxt.text = task.title
            viewTaskLinLayBinding.descTxt.text = task.description
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss a", Locale.getDefault())
            viewTaskLinLayBinding.dateTxt.text = dateFormat.format(task.date)
            viewTaskLinLayBinding.delImg.setOnClickListener {
                if(adapterPosition != -1){
                    deleteUpdateCallback("delete",adapterPosition,task)
                }
            }
            viewTaskLinLayBinding.editImg.setOnClickListener {
                if(adapterPosition!=-1){
                    deleteUpdateCallback("update",adapterPosition,task)
                }
            }
        }
    }

    class GridLayViewHolder(private val viewTaskGridLayBinding: ViewTaskGridLayoutBinding):RecyclerView.ViewHolder(viewTaskGridLayBinding.root)
    {
        fun bind(task:Task,deleteUpdateCallback:(action:String, position:Int, task:Task)->Unit){
            viewTaskGridLayBinding.titleTxt.text = task.title
            viewTaskGridLayBinding.descTxt.text = task.description
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss a", Locale.getDefault())
            viewTaskGridLayBinding.dateTxt.text = dateFormat.format(task.date)
            viewTaskGridLayBinding.delImg.setOnClickListener {
                if(adapterPosition != -1){
                    deleteUpdateCallback("delete",adapterPosition,task)
                }
            }
            viewTaskGridLayBinding.editImg.setOnClickListener {
                if(adapterPosition!=-1){
                    deleteUpdateCallback("update",adapterPosition,task)
                }
            }
        }
    }
    fun addAllTasks(newTaskList:List<Task>){
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==1){
            GridLayViewHolder(ViewTaskGridLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }else{
            LinearLayViewHolder(ViewTaskLinearLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = taskList[position]
        if(isList.value!!){
            (holder as LinearLayViewHolder).bind(task,deleteUpdateCallback)
        }else{
            (holder as GridLayViewHolder).bind(task,deleteUpdateCallback)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(isList.value!!){
            0
        }else{
            1
        }
    }
    override fun getItemCount(): Int {
        return taskList.size
    }
}