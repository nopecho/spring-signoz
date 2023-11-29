package io.nopecho.sleuth

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}

inline fun <reified T> T.log() = LoggerFactory.getLogger(T::class.java)!!