@file:OptIn(DelicateCoroutinesApi::class)

package com.rubenpla.coroutinesfoundaments

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    globalScope()

    readLine()
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

fun startMsg() {
    println("Starting coroutine -${Thread.currentThread().name}-")
}

fun endMsg() {
    println("Starting coroutine -${Thread.currentThread().name}- finished")
}
