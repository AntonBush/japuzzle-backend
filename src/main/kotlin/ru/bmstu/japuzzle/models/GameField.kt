package ru.bmstu.japuzzle.models

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import ru.bmstu.japuzzle.GameFieldDeserializer
import ru.bmstu.japuzzle.GameFieldSerializer
import java.awt.Color

@JsonSerialize(using = GameFieldSerializer::class)
@JsonDeserialize(using = GameFieldDeserializer::class)
open class GameField(
    open val width: Int,
    open val height: Int,
    open val colors: FieldColors,
    open val cells: List<List<Color>>
) {
    override operator fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (other !is GameField) {
            return false
        }
        return cells == other.cells
    }

    override fun hashCode(): Int {
        return cells.hashCode()
    }
}