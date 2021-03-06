package ru.bmstu.japuzzle.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.bmstu.japuzzle.Color
import ru.bmstu.japuzzle.entities.TaskEntity
import ru.bmstu.japuzzle.entities.UserEntity
import ru.bmstu.japuzzle.models.*
import ru.bmstu.japuzzle.models.gamefield.FieldColors
import ru.bmstu.japuzzle.models.gamefield.GameField
import ru.bmstu.japuzzle.models.gamefield.RandomGameField
import ru.bmstu.japuzzle.repositories.TaskRepository
import ru.bmstu.japuzzle.repositories.UserRepository
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
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

    private fun newTask(
        user: UserEntity,
        w: Int = 3,
        h: Int = 3,
        colors: Int = 2,
        image: BufferedImage? = null
    ): Task {
        if (w < 1 || h < 1) {
            throw IllegalArgumentException("cols < 1 || rows < 1; cols:$w; rows:$h")
        }
        if (colors < 2) {
            throw IllegalArgumentException("colors < 2; colors:$colors")
        }

        val clrs = COLORS.dropLast(max(0, COLORS.size - colors))
        val fc = FieldColors(clrs)
        val gf: GameField = if (image == null) {
            RandomGameField(w, h, fc)
        } else {
            GameField.from(image, w, h, fc)
        }

        return taskRepository.save(
            TaskEntity.newInstance(gf, user)
        ).toTask()
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
        return new(
            username,
            width,
            height,
            colors,
            null
        )
    }

    @PostMapping("/new")
    fun new(
        @RequestParam("user") username: String,
        @RequestParam(value = "columns", required = false) width: Int?,
        @RequestParam(value = "rows", required = false) height: Int?,
        @RequestParam(value = "colors", required = false) colors: Int?,
        @RequestPart(value = "picture", required = false) file: MultipartFile?
    ): ResponseEntity<Any?> {
        val user = userRepository.findByName(username) ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        return try {
            val w = width ?: 3
            val h = height ?: 3
            val clrs = colors ?: 2
            val img = if (file == null) {
                null
            } else {
                ImageIO.read(file.inputStream)
            }
            val created = newTask(user, w, h, clrs, img)

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
        @RequestBody solution: Solution
    ): ResponseEntity<Correctness> {
        val user = userRepository.findByName(username) ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val userTaskEntity = (taskRepository.findByUser(user) + getDefaultTasks()).find { te -> te.id == id }
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val task = userTaskEntity.toTask()
        val solutionColorField = solution.toColorField()
        val solutionField = GameField(
            solutionColorField[0].size,
            solutionColorField.size,
            task.gameField.colors,
            solution.toColorField()
        )
        val s = task.check(solutionField)
        userTaskEntity.solved = task.solved
        taskRepository.save(userTaskEntity)
        return ResponseEntity(Correctness(s), HttpStatus.OK)
    }

    data class Correctness(
        val correctness: Boolean
    )

    data class Solution(
        val solution: List<List<String>>
    ) {
        fun toColorField(): List<List<Color>> {
            return solution.map { list -> list.map { s -> Color(s) } }
        }
    }
}