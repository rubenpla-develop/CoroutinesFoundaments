package com.rubenpla.coroutinesfoundaments

fun main() {
    lambda()
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
