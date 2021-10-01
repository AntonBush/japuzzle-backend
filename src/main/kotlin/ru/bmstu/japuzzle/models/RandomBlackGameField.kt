package ru.bmstu.japuzzle.models

import java.awt.Color

class RandomBlackGameField(
    override val width: Int,
    override val height: Int
) : GameField {
    override val colors: List<Color>
        get() = listOf(Color.BLACK)
    override val cells: List<List<Color?>> = List(height) { randomizeLine(width) }

    override fun equals(other: Any?): Boolean {
        return cells == other
    }

    override fun hashCode(): Int {
        return cells.hashCode()
    }

    companion object {
        private fun randomizeLine(length: Int): List<Color?> {
            val line = ArrayList<Color?>()

            var free = length
            val leadingSpaces = (0..free).random()
            free -= leadingSpaces
            line.addAll(List(leadingSpaces) { null })

            while (free > 0) {
                val randomBlack = (1..free).random()
                free -= randomBlack
                line.addAll(List(randomBlack) { Color.BLACK })

                if (free > 0) {
                    val randomSpaces = (1..free).random()
                    free -= randomSpaces
                    line.addAll(List(randomSpaces) { null })
                }
            }
            return List(length) { i -> line[i] }
        }
    }
}