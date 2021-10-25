package ru.bmstu.japuzzle.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import ru.bmstu.japuzzle.TaskSerializer
import java.awt.Color

@JsonSerialize(using = TaskSerializer::class)
open class Task(
    open val id: Long,
    open val user: User,
    open val gameField: GameField
) {
    open var solved: Boolean = false
        protected set

    open fun check(solution: GameField): Boolean {
        val s = gameField.hints == gameField.hints
        solved = solved || s
        return s
    }


}