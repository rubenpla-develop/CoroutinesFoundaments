package com.rubenpla.coroutinesfoundaments

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main() {
    dispatchers()
}

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
