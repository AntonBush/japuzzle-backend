package ru.bmstu.japuzzle.controllers

import org.springframework.web.bind.annotation.*
import ru.bmstu.japuzzle.models.Hints
import ru.bmstu.japuzzle.models.LazyHintsTask
import ru.bmstu.japuzzle.models.RandomBlackGameField
import ru.bmstu.japuzzle.models.Task

@RestController
@RequestMapping("/task", params = ["userid"])
class TaskController {

    val task: Task = LazyHintsTask(RandomBlackGameField(5, 5))

    @GetMapping("/new")
    fun new(
        @RequestParam("userid") userId: String
    ): Hints? {
        return task.hints
    }

    @GetMapping("/list")
    fun list(
        @RequestParam("userid") userId: String
    ): List<Task>? {
        return List(1) { task }
    }

    @GetMapping("/info/{id}")
    fun info(
        @PathVariable id: Long,
        @RequestParam("userid") userId: String
    ): Task? {
        return task
    }

    @PostMapping("/check/{id}")
    fun check(
        @PathVariable id: Long,
        @RequestParam("userid") userId: String
    ): Boolean {
        return false
    }
}