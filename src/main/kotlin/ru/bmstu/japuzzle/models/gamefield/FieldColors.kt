package ru.bmstu.japuzzle.models.gamefield

import java.awt.Color

class FieldColors(
    fieldColors: List<Color>
) {
    init {
        val size = fieldColors.toSet().size
        if (size < 2) {
            throw IllegalArgumentException("unique field colors count must be >= 2; actual:$size")
        }
    }
    val backgroundColor = fieldColors[0]
    val colors = (fieldColors.toSet() - backgroundColor).toList()
}