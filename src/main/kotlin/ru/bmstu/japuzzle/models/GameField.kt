package ru.bmstu.japuzzle.models

import java.awt.Color

interface GameField {
    val width: Int
    val height: Int
    operator fun get(row: Int, column: Int): Color?
    override operator fun equals(other: Any?): Boolean
}