package com.rubenpla.coroutinesfoundaments

import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {
    lambda()
    threads()
}

fun threads() {
    println(multiThread(2, 3))
    multiThreadLambda(6, 8) { println("MultiThread Lambda Result: $it") }
}

fun multiThread(x: Int, y: Int): Int {
    var result = 0

    thread {
        Thread.sleep(someTime())
        result = x + y
    }

    Thread.sleep(2_200)
    return result
}

fun multiThreadLambda(x: Int, y: Int, callback : (result : Int) -> Unit) {
    var result = 0

    thread {
        Thread.sleep(someTime())
        result = x + y
        callback(result)
    }
}

fun lambda() {
    println(multi(22, 3))
    println(multiLambda(22, 3) { result ->
        println("Lambda result: $result")
    })
}

fun multiLambda(x: Int, y: Int, callback : (result : Int) -> Unit) {
    callback(x * y)
}

fun multi(x: Int, y: Int): Int {
    return x * y
}

fun someTime() : Long {
    return Random.nextLong(500, 2000)
}
