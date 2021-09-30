package ru.bmstu.japuzzle.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import ru.bmstu.japuzzle.GameFieldSerializer
import java.awt.Color

@JsonSerialize(using = GameFieldSerializer::class)
interface GameField {
    val width: Int
    val height: Int
    operator fun get(row: Int, column: Int): Color?
    override operator fun equals(other: Any?): Boolean
}