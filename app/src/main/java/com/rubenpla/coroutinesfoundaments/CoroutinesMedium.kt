package com.rubenpla.coroutinesfoundaments

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    //dispatchers()
    nestedCoroutines()
}

@OptIn(DelicateCoroutinesApi::class)
fun nestedCoroutines() {
    runBlocking {
        newTopic("Nested Coroutines")

        val job = launch {
            startMsg()

            launch(newSingleThreadContext("Any task thread #1")) {
                startMsg()
                delay(someTime())
                println("   - Any task thread #1")
                endMsg()
            }

            launch(Dispatchers.IO) {
                startMsg()

                launch(newSingleThreadContext("Any task sub-thread #2")) {
                        startMsg()
                        delay(someTime())
                        println("   - Any task sub-thread #2")
                        endMsg()
                }

                delay(someTime())
                println("   - Any task thread #2")
                endMsg()
            }

            var sum = 0
            (1..100).forEach {
                sum+=it
                delay(someTime()/100)
            }

            println("Sum = $sum")
            endMsg()
        }

        delay(someTime()/2)
        job.cancel()
        println("   ----- Job Cancelled")
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun dispatchers() {
    runBlocking {
        newTopic("Dispatchers")

        launch {
            startMsg()
            println("   none")
            endMsg()
        }

        launch(Dispatchers.IO) {
            startMsg()
            println("   IO")
            endMsg()
        }

        launch(Dispatchers.Unconfined) {
            startMsg()
            println("   Unconfined")
            endMsg()
        }

        //This Dispatchers works only for Android
/*
        launch(Dispatchers.Main) {
            startMsg()
            println("\u00A0\u00A0\u00A0 Unconfined")
            endMsg()
        }
*/

        launch(Dispatchers.Default) {
            startMsg()
            println("   Default")
            endMsg()
        }
        launch(newSingleThreadContext("Custom Thread")) {
            startMsg()
            println("   Custom Thread Coroutine with newSingleThreadContext()")
            endMsg()
        }

        newSingleThreadContext("Custom Thread #2").use { myContext ->
            launch(myContext) {
                startMsg()
                println("   Custom Thread Coroutine with newSingleThreadContext() #2")
                endMsg()
            }
        }
    }
}
