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

    private val DEFAULT_USER_NAME = "default";
    private val SOLVED_USER_NAME = "solved";
    private val DEFAULT_USER = userRepository.findByName(DEFAULT_USER_NAME)
        ?: userRepository.save(UserEntity(DEFAULT_USER_NAME))
    private val SOLVED_USER = userRepository.findByName(SOLVED_USER_NAME)
        ?: userRepository.save(UserEntity(SOLVED_USER_NAME))

    init {
        if (taskRepository.findByUser(DEFAULT_USER).isEmpty()) {
            newTask(DEFAULT_USER)
        }
        if (taskRepository.findByUser(SOLVED_USER).isEmpty()) {
            val st = newTask(SOLVED_USER)
            val te = taskRepository.findById(st.id).get()
            te.solved = true
            taskRepository.save(te)
        }
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

    private fun getDefaultTasks(): List<TaskEntity> {
        val defaultTask = taskRepository.findByUser(DEFAULT_USER)
        val solvedTask = taskRepository.findByUser(SOLVED_USER)
        return defaultTask + solvedTask
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
        val userTasks = (taskRepository.findByUser(user) + getDefaultTasks()).map { te -> te.toTask() }
        return ResponseEntity(userTasks, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun info(
        @PathVariable id: Long,
        @RequestParam("user") username: String
    ): ResponseEntity<Task> {
        val user = userRepository.findByName(username) ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val userTask = (taskRepository.findByUser(user) + getDefaultTasks()).find { te -> te.id == id }?.toTask()
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(userTask, HttpStatus.OK)
    }

    @PostMapping("/check/{id}")
    fun check(
        @PathVariable id: Long,
        @RequestParam("user") username: String,
        @RequestBody solution: GameField
    ): ResponseEntity<Task> {
        val user = userRepository.findByName(username) ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val userTaskEntity = (taskRepository.findByUser(user) + getDefaultTasks()).find { te -> te.id == id }
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val task = userTaskEntity.toTask()
        userTaskEntity.solved = task.check(solution)
        taskRepository.save(userTaskEntity)
        return ResponseEntity(task, HttpStatus.OK)
    }
}