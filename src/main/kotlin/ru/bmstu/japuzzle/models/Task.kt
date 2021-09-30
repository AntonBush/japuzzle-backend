package ru.bmstu.japuzzle.models

import java.awt.Color

interface Task {
    val solved: Boolean
    val hints: Hints
    val gameField: GameField?

    fun check(solution: GameField): Boolean
}