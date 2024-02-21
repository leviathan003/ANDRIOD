package com.example.coroutines_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutines_demo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

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

//        CoroutineScope(Dispatchers.Main).launch {
//            task1()
//            task2()
//        }

        CoroutineScope(Dispatchers.Main).launch {
            printFollowers()
        }

    }

    // When return value is cared about or expected i.e. data return is needed
    suspend fun printFollowers(){
        val job = CoroutineScope(Dispatchers.IO).async {
            getFollowers()
        }
        println(job.await().toString())
    }

    //A way to combine launch and async coroutine builders
//    suspend fun printFollowers(){
//        //var followerCount=0
//        val job = CoroutineScope(Dispatchers.IO).launch {
//            var followerCount = async {getFollowers()}
//            println(followerCount.await().toString())
//        }
//        //job.join()
//    }

    // Fire & forget (not care about return)
//    suspend fun printFollowers(){
//        var followerCount=0
//        val job = CoroutineScope(Dispatchers.IO).launch {
//            followerCount = getFollowers()
//        }
//        job.join()
//        println(followerCount)
//    }

    suspend fun getFollowers():Int{
        delay(2000)
        return 54
    }

    suspend fun task1(){
        println("Hello1")
        yield()
        println("Bye1")
    }

    suspend fun task2(){
        println("Hello2")
        yield()
        println("Bye2")
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