package ru.bmstu.japuzzle.models

interface Hints {
    val rows: List<List<Hint>>
    val columns: List<List<Hint>>
}