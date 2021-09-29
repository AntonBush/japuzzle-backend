package ru.bmstu.japuzzle.models

import java.awt.Color

interface Hints {
    val lines: List<Hint>
    val columns: List<Hint>
}