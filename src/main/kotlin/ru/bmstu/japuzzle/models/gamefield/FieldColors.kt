package ru.bmstu.japuzzle.models

import java.awt.Color

class FieldColors(
    fieldColors: List<Color>
) {
    init {
        val size = fieldColors.size
        if (size < 1) {
            throw IllegalArgumentException("field colors count must be greater than 2; actual:$size")
        }
    }
    val backgroundColor = fieldColors[0]
    val colors = (fieldColors.toSet() - backgroundColor).toList()
}