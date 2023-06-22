@file:OptIn(DelicateCoroutinesApi::class)

package com.rubenpla.coroutinesfoundaments

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    //globalScope()
    //suspendFun()
    newTopic("Coroutines constructors")
    //cRunBLocking()
    //cLaunch()
    //cAsync()
    //job()
    //deferred()
    cProduce()
    readLine()
}

fun cProduce() = runBlocking {
    newTopic("Produce")
    val names = produceNames()
    names.consumeEach { println(it) }
}

fun CoroutineScope.produceNames(): ReceiveChannel<String> = produce {
    (1..5).forEach { send("name $it") }
}

fun deferred() {
    runBlocking {
        newTopic("Deferred")
        val deferred = async {
            startMsg()
            delay(someTime())
            println("deferred...")
            endMsg()
            multi(5, 9)
        }

        println("Deferred : $deferred")
        println("Deferred Value : ${deferred.await()}")

        val result = async {
            multi(3, 3)
        }.await()

        println("Result: $result")


    }
}

fun job() {
    runBlocking {
        newTopic("Job")
        val job = launch {
            startMsg()
            delay(2_100)
            println("Job...")
            endMsg()
        }
        println("Job : $job")
        //delay(2_220) //this lines for completed Job
        println("isActive: ${job.isActive}")
        println("isCancelled: ${job.isCancelled}")
        println("isCompleted: ${job.isCompleted}")

        //this block for cancelled Job
        println("Task cancelled or interrupted")
        job.cancel()

        println("isActive: ${job.isActive}")
        println("isCancelled: ${job.isCancelled}")
        println("isCompleted: ${job.isCompleted}")
    }
}

fun cRunBLocking() {
    newTopic("Run blocking")
    runBlocking {
        startMsg()
        delay(someTime())
        println("runBlocking")
        endMsg()
    }
}

fun cLaunch() {
    newTopic("Run blocking")
    runBlocking {
        newTopic("launch")
        launch {
            startMsg()
            delay(someTime())
            println("launch")
            endMsg()
        }
    }
}

fun cAsync() {
    newTopic("Run blocking")
    runBlocking {
        newTopic("launch")
        val result = async {
            startMsg()
            delay(someTime())
            println("launch")
            endMsg()

            "Result value"
        }

        println("Result : ${result.await()}")
    }
}


fun globalScope() {
    newTopic("Global Scope")
    GlobalScope.launch {
        startMsg()
        delay(someTime())
        println("My Coroutine")
        endMsg()
    }
}

fun suspendFun() {
    newTopic("Global Scope")
    Thread.sleep(someTime())
    GlobalScope.launch {
        delay(someTime())
    }
}

fun startMsg() {
    println("Starting coroutine -${Thread.currentThread().name}-")
}

fun endMsg() {
    println("Starting coroutine -${Thread.currentThread().name}- finished")
}
