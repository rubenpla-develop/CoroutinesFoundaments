package com.rubenpla.coroutinesfoundaments

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() {
        //coldFlow()
        cancelFlow()
}

fun coldFlow() {
        newTopic("Flows are cold")

        runBlocking {
                val dataFlow = provideFlow()
                println("waiting...")
                delay(someTime())
                dataFlow.collect { element -> println(element) }
        }
}

fun cancelFlow() {
        runBlocking {
                newTopic("Cancel Flow")
                val job = launch {
                        provideFlow().collect { element ->
                                println(element)
                                delay(someTime() / 2)
                        }
                }

                delay(someTime())
                job.cancel(CancellationException("Job cancelled manually"))
        }

}

fun provideFlow() : Flow<Float> {
        return flow {
                (1..5).forEach {
                        println("Processing data")
                        Thread.sleep(someTime())
                        emit(20 + it + Random.nextFloat())
                }
        }
}

