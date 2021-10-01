package ru.bmstu.japuzzle.controllers

import org.springframework.web.bind.annotation.*
import ru.bmstu.japuzzle.models.Hints
import ru.bmstu.japuzzle.models.CheckToSolveTask
import ru.bmstu.japuzzle.models.RandomBlackGameField
import ru.bmstu.japuzzle.models.Task

@RestController
@RequestMapping("/task", params = ["user"])
class TaskController {

    val newTask: () -> Task = {
        CheckToSolveTask(RandomBlackGameField(3, 3))
    }

    val task: Task = newTask()

    init {
        task.check(task.gameField)
    }

    @GetMapping("/new")
    fun new(
        @RequestParam("user") userId: String
    ): Task? {
        return newTask()
    }

    @GetMapping("/list")
    fun list(
        @RequestParam("user") userId: String
    ): List<Task>? {
        return List(1) { task }
    }

    @GetMapping("/info/{id}")
    fun info(
        @PathVariable id: Long,
        @RequestParam("user") userId: String
    ): Task? {
        return task
    }

    @PostMapping("/check/{id}")
    fun check(
        @PathVariable id: Long,
        @RequestParam("user") userId: String
    ): Boolean {
        return false
    }
}