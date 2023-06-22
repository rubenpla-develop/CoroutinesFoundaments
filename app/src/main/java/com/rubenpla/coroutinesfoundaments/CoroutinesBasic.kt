@file:OptIn(DelicateCoroutinesApi::class)

package com.rubenpla.coroutinesfoundaments

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    //globalScope()
    //suspendFun()
    newTopic("Coroutines constructors")
    //cRunBLocking()
    cLaunch()
    readLine()
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
