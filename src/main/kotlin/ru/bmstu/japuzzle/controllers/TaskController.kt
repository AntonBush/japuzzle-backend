package ru.bmstu.japuzzle.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.bmstu.japuzzle.entities.GameFieldEmbeddable
import ru.bmstu.japuzzle.entities.TaskEntity
import ru.bmstu.japuzzle.entities.UserEntity
import ru.bmstu.japuzzle.models.*
import ru.bmstu.japuzzle.repositories.TaskRepository
import ru.bmstu.japuzzle.repositories.UserRepository
import ru.bmstu.japuzzle.rgbToHex
import java.awt.Color
import kotlin.math.max

@CrossOrigin
@RestController
@RequestMapping("/task")
class TaskController @Autowired constructor(
    private val userRepository: UserRepository,
    private val taskRepository: TaskRepository,
) {
    private val COLORS = listOf<Color>(Color.WHITE, Color.BLACK, Color.RED, Color.GREEN, Color.BLUE)

    private val DEFAULT_USER = userRepository.save(UserEntity("default"))
    private val SOLVED_USER = userRepository.save(UserEntity("solved"))

    init {
        newTask(DEFAULT_USER)
        val st = newTask(SOLVED_USER)
        taskRepository.findById(st.id).get().solved = true
    }

    private fun newTask(user: UserEntity,
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
        val gf = RandomGameField(w, h, fc)

        return taskRepository.save(TaskEntity(
            user,
            false,
            GameFieldEmbeddable(
                gf.width,
                gf.height,
                gf.colors.backgroundColor.rgbToHex(),
                gf.colors.colors.mapIndexed { i, c ->
                    i to c.rgbToHex()
                }.toMap(),
                gf.cells.flatMapIndexed { i,  l ->
                    l.toList().mapIndexed { j, c ->
                        (i * gf.width + j) to c.rgbToHex()
                    }
                }.toMap()
            ),
        )).toTask()
    }

    @GetMapping("/new")
    fun new(
        @RequestParam("user") username: String,
        @RequestParam(value = "columns", required = false) width: Int?,
        @RequestParam(value = "rows", required = false) height: Int?,
        @RequestParam(value = "colors", required = false) colors: Int?,
    ): ResponseEntity<Any?> {
        val user = userRepository.findByName(username) ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        return try {
            val w = width ?: 3
            val h = height ?: 3
            val clrs = colors ?: 2
            val created = newTask(user, w, h, clrs)
            ResponseEntity(created, HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/list")
    fun list(
        @RequestParam("user") username: String
    ): ResponseEntity<List<Task>?> {
        val user = userRepository.findByName(username) ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val userTasks = taskRepository.findByUser(user)?.map { te -> te.toTask() } ?: listOf()
        val defaultTask = taskRepository.findByUser(DEFAULT_USER)?.map { te -> te.toTask() } ?: listOf()
        val solvedTask = taskRepository.findByUser(SOLVED_USER)?.map { te -> te.toTask() } ?: listOf()
        return ResponseEntity(userTasks + defaultTask + solvedTask, HttpStatus.OK)
    }

    @GetMapping("/info/{id}")
    fun info(
        @PathVariable id: Long,
        @RequestParam("user") username: String
    ): ResponseEntity<Task> {
        val user = userRepository.findByName(username) ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val task = taskRepository.findById(user.toUser().id)
        if (task.isEmpty) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(task.get().toTask(), HttpStatus.OK)
    }

    @PostMapping("/check/{id}")
    fun check(
        @PathVariable id: Long,
        @RequestParam("user") username: String,
        @RequestBody solution: GameField
    ): ResponseEntity<Task> {
        val user = userRepository.findByName(username) ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val task = taskRepository.findById(user.toUser().id)
        if (task.isEmpty) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        val t = task.get().toTask()
        task.get().solved = t.check(solution)
        return ResponseEntity(t, HttpStatus.OK)
    }
}