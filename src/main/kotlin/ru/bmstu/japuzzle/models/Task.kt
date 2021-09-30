package ru.bmstu.japuzzle.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import ru.bmstu.japuzzle.TaskSerializer

@JsonSerialize(using = TaskSerializer::class)
interface Task {
    val solved: Boolean
    val hints: Hints
    val gameField: GameField?

    fun check(solution: GameField): Boolean
}