package ru.bmstu.japuzzle

import ru.bmstu.japuzzle.models.Player
import ru.bmstu.japuzzle.models.Task

interface TaskCreator {
    fun createTask(id: Long, player: Player): Task
}
