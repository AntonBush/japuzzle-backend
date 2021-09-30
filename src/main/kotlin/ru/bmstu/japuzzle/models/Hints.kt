package ru.bmstu.japuzzle.models

data class Hints(
    val rows: List<List<Hint>>,
    val columns: List<List<Hint>>
)