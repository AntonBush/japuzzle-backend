package ru.bmstu.japuzzle.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.bmstu.japuzzle.models.*
import ru.bmstu.japuzzle.repositories.UserRepository
import java.awt.Color
import kotlin.math.max

@CrossOrigin
@RestController
@RequestMapping("/task")
class TaskController @Autowired constructor(
    val userRepository: UserRepository
) {
    val COLORS = listOf<Color>(Color.WHITE, Color.BLACK, Color.RED, Color.GREEN, Color.BLUE)

    fun newTask(user: User,
                taskId: Long = 0L,
                w: Int = 3,
                h: Int = 3,
                colors: Int = 2
    ): Task {
        if (w < 1 || h < 1) {
            throw IllegalArgumentException("cols < 1 || rows < 1; cols:$w; rows:$h")
        }
        if (colors < 2) {
            throw IllegalArgumentException("colors < 2; colors:$colors")
        }
        val clrs = COLORS.dropLast(max(0, COLORS.size - colors))
        val fc = FieldColors(clrs)

        return SecureTask(
            taskId,
            user,
            RandomGameField(3, 3, fc))
    }

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
        @RequestParam("user") username: String,
        @RequestParam(value = "columns", required = false) width: Int = 3,
        @RequestParam(value = "rows", required = false) height: Int = 3,
        @RequestParam(value = "colors", required = false) colors: Int = 2,
    ): ResponseEntity<Any?> {
        val user = userRepository.findByName(username)?.toUser() ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        return try {
            val created = newTask(user, tasks.size.toLong())
            tasks.add(created)
            ResponseEntity(created, HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
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