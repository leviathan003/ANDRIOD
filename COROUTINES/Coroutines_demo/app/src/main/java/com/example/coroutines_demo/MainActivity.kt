package com.example.coroutines_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutines_demo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private var count=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.strtCountBtn.setOnClickListener {
            strtCounting()
        }
        binding.strtDownloadBtn.setOnClickListener {
            strtDowloading()
        }
    }

    private fun strtCounting(){
            binding.countDisp.text = count++.toString()
    }

    private fun strtDowloading(){
        CoroutineScope(Dispatchers.IO).launch {
            for(i in 1..1000000){
                println("${i} : ${Thread.currentThread().name}")
            }
        }
    }
}