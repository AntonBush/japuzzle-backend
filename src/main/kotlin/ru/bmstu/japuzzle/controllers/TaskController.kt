package ru.bmstu.japuzzle.controllers

import org.springframework.web.bind.annotation.*
import ru.bmstu.japuzzle.models.*

@CrossOrigin
@RestController
@RequestMapping("/task", params = ["user"])
class TaskController {

    val newTask: (String) -> Task = { username ->
        CheckToSolveTask(0L, User(0L, username), RandomBlackGameField(3, 3))
    }

    val task: Task = newTask("default")

    init {
        task.check(task.gameField)
    }

    @GetMapping("/new")
    fun new(
        @RequestParam("user") user: String
    ): Task? {
        return newTask(user)
    }

    @GetMapping("/list")
    fun list(
        @RequestParam("user") user: String
    ): List<Task>? {
        return List(1) { task }
    }

    @GetMapping("/info/{id}")
    fun info(
        @PathVariable id: Long,
        @RequestParam("user") user: String
    ): Task? {
        return task
    }

    @PostMapping("/check/{id}")
    fun check(
        @PathVariable id: Long,
        @RequestParam("user") user: String
    ): Boolean {
        return false
    }
}