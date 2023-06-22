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
    cRunBLocking()
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
