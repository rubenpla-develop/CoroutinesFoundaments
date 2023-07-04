package com.rubenpla.coroutinesfoundaments

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlin.random.Random

fun main() {
    //dispatchers()
    //nestedCoroutines()
    //changeWithContext()
    //sequences()
    basicFlows()
}

fun basicFlows() {
    newTopic("Basic Flows")
    runBlocking {
        launch {
            getDataByFlow().collect() {
                println(it)
            }
        }

        launch {
            (1..50).forEach {
               // delay(someTime()/10)
                println("Another task $it")
            }
        }
    }
}

/*fun sequences() {
    newTopic("Sequences")
    getDataByFlow().forEach { println("$it Gradius ") }
}*/

fun getDataByFlow() : Flow<Float> {
    return flow {
        (1..5).forEach {
            println("Processing data")
            Thread.sleep(someTime())
            emit(20 + it + Random.nextFloat())
        }
    }
}

fun changeWithContext() {
    runBlocking {
        newTopic("WithContext")
        startMsg()

        withContext(newSingleThreadContext("Thread #1")) {
            startMsg()
            delay(someTime())
            println("   - Thread #1")

            endMsg()
        }

        withContext(Dispatchers.IO) {
            startMsg()
            delay(someTime())
            println("   - Thread #2")

            endMsg()
        }

        endMsg()
    }
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
