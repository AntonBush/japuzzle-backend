package ru.bmstu.japuzzle.models

import java.awt.Color

open class RandomGameField(
    width: Int,
    height: Int,
    colors: FieldColors
) : GameField(
    width,
    height,
    colors,
    List(height) {
        val line = ArrayList<Color>()

        var free = width
        val leadingSpaces = (0..free).random()
        free -= leadingSpaces
        line.addAll(List(leadingSpaces) { colors.backgroundColor })

        while (free > 0) {
            val randomBlack = (1..free).random()
            free -= randomBlack
            line.addAll(List(randomBlack) { colors.colors[(0 until colors.colors.size).random()] })

            if (free > 0) {
                val randomSpaces = (1..free).random()
                free -= randomSpaces
                line.addAll(List(randomSpaces) { colors.backgroundColor })
            }
        }
        return@List List(width) { i -> line[i] }
    }
)