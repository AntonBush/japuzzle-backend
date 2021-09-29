package ru.bmstu.japuzzle.models

interface Task {
    val id: Long
    val solved: Boolean
    val player: Player
    val hints: Hints
}