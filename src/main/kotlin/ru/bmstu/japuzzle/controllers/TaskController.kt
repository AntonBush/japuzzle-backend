package ru.bmstu.japuzzle.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.bmstu.japuzzle.models.*
import ru.bmstu.japuzzle.repositories.UserRepository

@CrossOrigin
@RestController
@RequestMapping("/task")
class TaskController @Autowired constructor(
    val userRepository: UserRepository
) {
    fun newTask(user: User, taskId: Long = 0L): Task {
        return SecureTask(taskId, user, RandomBlackAndWightGameField(3, 3))
    }

    fun newTask(username: String, taskId: Long = 0L): Task {
        return newTask(User(0L, username), taskId)
    }

    val tasks: MutableList<Task> = mutableListOf(newTask("default", 0), newTask("solved", 1))

    init {
        val solvedTask = tasks.find { t -> t.user.name == "solved" }
        solvedTask!!.check(solvedTask.gameField)
    }

    @GetMapping("/new")
    fun new(
        @RequestParam("user") username: String
    ): ResponseEntity<Task?> {
        val user = userRepository.findByName(username)?.toUser() ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val created = newTask(user, tasks.size.toLong())
        tasks.add(created)
        return ResponseEntity(created, HttpStatus.CREATED)
    }

    @GetMapping("/list")
    fun list(
        @RequestParam("user") username: String
    ): ResponseEntity<List<Task>?> {
        if (userRepository.existsByName(username).not()) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        return ResponseEntity(tasks, HttpStatus.OK)
    }

    @GetMapping("/info/{id}")
    fun info(
        @PathVariable id: Long,
        @RequestParam("user") user: String
    ): ResponseEntity<Task?> {
        if (userRepository.existsByName(user).not()) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        val task = tasks.find { t -> t.id == id } ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(task, HttpStatus.OK)
    }

    @PostMapping("/check/{id}")
    fun check(
        @PathVariable id: Long,
        @RequestParam("user") user: String,
        @RequestBody solution: GameField
    ): ResponseEntity<Boolean> {
        if (userRepository.existsByName(user).not()) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        val task = tasks.find { t -> t.id == id } ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(task.check(solution), HttpStatus.OK)
    }
}