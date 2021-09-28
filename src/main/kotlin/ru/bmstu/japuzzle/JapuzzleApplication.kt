package ru.bmstu.japuzzle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JapuzzleApplication

fun main(args: Array<String>) {
    runApplication<JapuzzleApplication>(*args)
}
