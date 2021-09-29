package ru.bmstu.japuzzle.controllers

import org.springframework.web.bind.annotation.*
import ru.bmstu.japuzzle.models.Hints
import ru.bmstu.japuzzle.models.Task

@RestController
@RequestMapping("/task", params = ["userid"])
class TaskController {

    @GetMapping("/new")
    fun new(
        @RequestParam("userid") userId: String
    ): Hints? {
        return null
    }

    @GetMapping("/list")
    fun list(
        @RequestParam("userid") userId: String
    ): List<Task>? {
        return null
    }

    @GetMapping("/info/{id}")
    fun info(
        @PathVariable id: Long,
        @RequestParam("userid") userId: String
    ): Task? {
        return null
    }

    @PostMapping("/check/{id}")
    fun check(
        @PathVariable id: Long,
        @RequestParam("userid") userId: String
    ): Boolean {
        return false
    }
}