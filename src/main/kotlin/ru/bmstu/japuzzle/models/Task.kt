package ru.bmstu.japuzzle.models

import java.awt.Color

interface Task {
    val id: Long
    val solved: Boolean
    val player: Player
    val hints: Hints

    fun check(solution: List<List<Color?>>): Boolean
}