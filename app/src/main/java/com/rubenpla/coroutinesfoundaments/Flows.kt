package com.rubenpla.coroutinesfoundaments

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    //coldFlow()
    //cancelFlow()
    //flowOperators()
    //terminalFlowOperators()
    //bufferFlow()
    conflationFlow()
}

fun conflationFlow() {
    runBlocking {
        newTopic("Conflation Flow")
        val time = measureTimeMillis {
            getMatchResultsFlow()
                .conflate() //2558ms
                //.buffer() //4820ms
                //.collectLatest { //2597ms
                .collect() {//7087ms
                    delay(100)
                    println(it)
                }
        }

        println("Time: ${time}ms")
    }
}

fun getMatchResultsFlow(): Flow<String> {
    return flow {
        var homeTeam = 0
        var awayTeam = 0
        (0..45).forEach {
            println("minute : $it")
            delay(50)
            homeTeam += Random.nextInt(0, 21)/20
            awayTeam += Random.nextInt(0, 21)/20
            emit("$homeTeam-$awayTeam")
        }
    }
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

fun terminalFlowOperators() {
    runBlocking {
        newTopic("Terminal Flow Operators")
        newTopic("ToList")

        val list = provideFlow()
        //     .toList()
        //println("List: $list")

        newTopic("Single")
        val single = provideFlow()
//                        .take(1)
//                        .single()

        //println("Single: $single")

        newTopic("First")
        val first = provideFlow()
        //.first()
        //println("First: $first")

        newTopic("Last")
        val last = provideFlow()
        //.last()
        //println("last: $last")

        newTopic("Reduce")
        val saving = provideFlow()
            .reduce { accumulator, value ->
                println("Accumulator: $accumulator")
                println("Value: $value")
                println("Current saving: ${accumulator + value}")
                accumulator + value
            }
        println("Saving: $saving")

        newTopic("Fold")
        val lastSaving = saving
        val totalSaving = provideFlow()
            .fold(lastSaving) { acc, value ->
                println("Accumulator: $acc")
                println("Value: $value")
                println("Current saving: ${acc + value}")
                acc + value
            }

        println("Total Saving: $totalSaving")
    }
}

fun bufferFlow() {
    runBlocking {
        newTopic("Buffer for Flow")
        val time = measureTimeMillis {
            provideFlowStatic().map {
                setFormat(it)
            }
                .buffer()
                .collect() {
                    delay(500)
                    println(it)
                }
        }
        println("Time : ${time}ms")
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
            .filter { elementToFilter ->
                elementToFilter < 23
            }
            .map {
                setFormat(it)
            }
        //.collect { println(it) }

        //For more complex processes, it is expected to emit 1 value minimum, so its possible
        // to emit multiple values
        newTopic("Transform")
        provideFlow()
            .transform {
                emit(setFormat(it))
                emit(setFormat(parseFromCelsToFahr(it), "F"))
            }
        //.collect { println(it)}

        newTopic("Take")
        provideFlow()
            .take(1)
            .map { setFormat(it) }
            .collect { println(it) }

    }
}

fun parseFromCelsToFahr(cels: Float): Float = ((cels * 9) / 5) + 32

fun setFormat(heat: Float, degree: String = "C"): String =
    String.format(Locale.getDefault(), "%.1fº$degree", heat)


fun provideFlow(): Flow<Float> {
    return flow {
        (1..5).forEach {
            println("Processing data")
            Thread.sleep(someTime())
            emit(20 + it + Random.nextFloat())
        }
    }
}

fun provideFlowStatic(): Flow<Float> {
    return flow {
        (1..5).forEach {
            println("Processing data")
            delay(300)
            emit(20 + it + Random.nextFloat())
        }
    }
}

