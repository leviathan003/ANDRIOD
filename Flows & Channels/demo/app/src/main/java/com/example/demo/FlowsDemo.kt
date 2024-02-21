package com.example.demo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class FlowsDemo {

//Flows Introductions:
//    var job = GlobalScope.launch {
//            val data = FlowsDemo.producer()
//            data.collect {
//                println(it.toString())
//            }
//        }

//        GlobalScope.launch {
//            delay(3500)
//            job.cancel()
//        }

//    GlobalScope.launch {
//        val data = FlowsDemo.producer()
//        data.collect {
//            println(it.toString())
//        }
//    }
//
//    GlobalScope.launch {
//        val data = FlowsDemo.producer()
//        delay(3000)
//        data.collect {
//            println(it.toString())
//        }
//    }
//}

//    fun producer()= flow<Int>{
//        val list = listOf(1,2,3,4,5,6,7,8,9,10)
//        list.forEach {
//            delay(1000)
//            emit(it)
//        }
//    }

//    GlobalScope.launch(Dispatchers.Main) {
//        FlowsDemo.producer().onStart {
//            emit(-1)
//            println("Starting")
//        }.onCompletion {
//            println("Done")
//        }.onEach {
//            println("About to emit: $it")
//        }.collect{
//            println("${it.toString()}")
//        }
//    }

//Flows Operators:
//Terminal operators (needed to start the flow)
//  .collect() operator to collect the outputs
//  .first() operator to return 1st element of the flow
//  .toList() operator to change the entire flow output to list

//Non terminal operators
//    .map{} operator to map values into other objects
//    .filter{} operator will filter based on criteria eg: .filter{it<8}
//    .buffer(capacity) operator will be used to store the collected item when the consumption time is more
//    than production time

//    .flowOn(Dispatcher_name) is a function used to perform context switching between two threads
//    so that the emission and collection can be performed on different threads without errors, i.e.
//    context switching can be done smoothly and it works upstream
//    GlobalScope.launch(Dispatchers.Main) {
//        FlowsDemo.producer().flowOn(Dispatchers.IO)
//            .collect{
//                println("${it.toString()},${Thread.currentThread().name}")
//            }
//    }

    companion object{
        fun producer()= flow<Int>{
            val list = listOf(1,2,3,4,5,6,7,8,9,10)
            list.forEach {
                delay(1000)
                println(Thread.currentThread().name.toString())
                emit(it)
                throw Exception("Error in Emitter")
            }
        }.catch {
            println(it.message)
            // additional values can also be emitted and it works upstream
            emit(-1)
        }
    }

}