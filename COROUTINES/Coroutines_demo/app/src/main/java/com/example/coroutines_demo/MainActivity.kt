package com.example.coroutines_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutines_demo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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

//        CoroutineScope(Dispatchers.Main).launch {
//            printFollowers()
//        }

//        Jobs Hierarchy
//        val parent = GlobalScope.launch(Dispatchers.Main) {
//            println("parent - ${coroutineContext}")
//            launch(Dispatchers.IO) {
//                println("child - ${coroutineContext}")
//            }
//        }

//        Until child job is done executing the parent will not be completed
//        var parent = GlobalScope.launch(Dispatchers.Main) {
//            println("parent - ${coroutineContext}")
//            var child = launch(Dispatchers.IO) {
//                delay(5000)
//                println("child - ${coroutineContext}")
//            }
//            Cancel only child job
//            child.cancel()
//            delay(3000)
//        }
//        parent job cancelled so child job is also cancelled
//        delay(1000)
//        parent.cancel()
//        parent.join()

//        Manual cancellation of job using isActive condition to check if coroutine is still active or not
//        val parentJob = CoroutineScope(Dispatchers.IO).launch {
//            for(i in 1..1000){
//                if(isActive){
//                    executelongrunningTask()
//                    println(i.toString())
//                }
//            }
//        }
//        delay(100)
//        println("Cancelling Job")
//        parentJob.cancel()
//        parentJob.join()
//        println("Parent complete")

//        withContext is a coroutine builder like launch and async but it will block the code and after the execution of the
//        stuff inside the withContext block the next lines will be executed unlike in launch where the launch block is skipped and
//        executed later this is primarily used for context switching between threads or coroutines that run on threads
//        println("Before")
//        withContext(Dispatchers.Main){
//            delay(1000)
//            println("Inside")
//        }
//        println("After")

//        This is runBlocking which is used to block a thread for complete execution of the body inside
//        if this was used in a normal GlobalScope then the program would stop after printing hello unless
//        made to sleep for 2000ms
//        fun main() = runBlocking {
//            launch {
//                delay(1000)
//                println("World")
//            }
//            println("Hello")
//        }

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