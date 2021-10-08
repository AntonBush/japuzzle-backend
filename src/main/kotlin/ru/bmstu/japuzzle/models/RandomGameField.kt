package ru.bmstu.japuzzle.models

import java.awt.Color

open class RandomGameField(
    width: Int,
    height: Int,
    colors: FieldColors
) : GameField(
    width,
    height,
    colors
) {
    override val cells: List<List<Color>> = List(height) { randomizeLine(width) }

    private fun randomizeLine(length: Int): List<Color> {
        val line = ArrayList<Color>()

        var free = length
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
        return List(length) { i -> line[i] }
    }
}