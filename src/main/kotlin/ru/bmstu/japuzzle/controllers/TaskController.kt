package ru.bmstu.japuzzle.controllers

import org.springframework.web.bind.annotation.*
import ru.bmstu.japuzzle.models.*

@CrossOrigin
@RestController
@RequestMapping("/task", params = ["user"])
class TaskController {

    fun newTask(username: String, taskId: Long = 0L): Task {
        return CheckToSolveTask(taskId, User(0L, username), RandomBlackGameField(3, 3))
    }

    val tasks: List<Task> = listOf(newTask("default", 0), newTask("solved", 1))

    init {
        val solvedTask = tasks.find { t -> t.user.name == "solved" }
        solvedTask!!.check(solvedTask.gameField)
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
        return tasks
    }

    @GetMapping("/info/{id}")
    fun info(
        @PathVariable id: Long,
        @RequestParam("user") user: String
    ): Task? {
        return tasks.find { t -> t.id == id }
    }

    @PostMapping("/check/{id}")
    fun check(
        @PathVariable id: Long,
        @RequestParam("user") user: String
    ): Boolean {
        return false
    }
}