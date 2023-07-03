package com.rubenpla.coroutinesfoundaments

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    dispatchers()
}

fun dispatchers() {
    runBlocking {
        newTopic("Dispatchers")

        launch {
            startMsg()
            println("none")
            endMsg()
        }

        launch(Dispatchers.IO) {
            startMsg()
            println("IO")
            endMsg()
        }

        launch(Dispatchers.Unconfined) {
            startMsg()
            println("Unconfined")
            endMsg()
        }
    }
}
