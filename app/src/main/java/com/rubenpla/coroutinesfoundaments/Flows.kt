package com.rubenpla.coroutinesfoundaments

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale
import kotlin.random.Random

fun main() {
        //coldFlow()
        //cancelFlow()
        flowOperators()
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

fun flowOperators() {
        runBlocking {
                newTopic("Intermediate Flow Operators")

                newTopic("Map")
                //With map you can call a suspend fun inside block
                provideFlow().map { element ->
                        //setFormat(element)
                        setFormat(parseFromCelsToFahr(element), "F")
                }
                        //.collect { formattedElement -> println(formattedElement) }

                newTopic("Filter")
                provideFlow()
                        .filter {
                                elementToFilter -> elementToFilter < 23
                        }
                        .map {
                                setFormat(it)
                        }
                        .collect { println(it) }

        }
}

fun parseFromCelsToFahr(cels : Float): Float = ((cels * 9) /5) + 32

fun setFormat(heat : Float, degree: String = "C") : String = String.format(Locale.getDefault(), "%.1fº$degree", heat)



fun provideFlow() : Flow<Float> {
        return flow {
                (1..5).forEach {
                        println("Processing data")
                        Thread.sleep(someTime())
                        emit(20 + it + Random.nextFloat())
                }
        }
}

