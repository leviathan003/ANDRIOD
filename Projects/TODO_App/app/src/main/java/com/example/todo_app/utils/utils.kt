package com.example.todo_app.utils

import android.app.Dialog
import android.content.Context
import android.widget.LinearLayout
import android.widget.Toast

enum class Status{
    SUCCESS,
    ERROR,
    LOADING
}

fun Context.longToastShow(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}

fun Dialog.setupDialog(layoutResId: Int){
    setContentView(layoutResId)
    window!!.setLayout(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    setCancelable(false)
}