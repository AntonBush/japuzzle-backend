package ru.bmstu.japuzzle.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import ru.bmstu.japuzzle.HintsSerializer

@JsonSerialize(using = HintsSerializer::class)
data class Hints(
    val rows: List<List<Hint>>,
    val columns: List<List<Hint>>
)