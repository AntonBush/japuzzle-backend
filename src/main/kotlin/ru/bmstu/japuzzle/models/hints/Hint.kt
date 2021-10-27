package ru.bmstu.japuzzle.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import ru.bmstu.japuzzle.GameFieldSerializer
import ru.bmstu.japuzzle.HintSerializer
import java.awt.Color

@JsonSerialize(using = HintSerializer::class)
data class Hint(
    val color: Color,
    val count: Int
)