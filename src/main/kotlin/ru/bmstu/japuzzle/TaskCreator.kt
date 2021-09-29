package ru.bmstu.japuzzle

import ru.bmstu.japuzzle.models.Task

interface TaskCreator {
    fun createTask(): Task
}
