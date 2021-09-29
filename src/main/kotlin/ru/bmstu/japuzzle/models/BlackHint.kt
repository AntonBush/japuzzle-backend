package ru.bmstu.japuzzle.models

import java.awt.Color

data class BlackHint(
    override val count: Int
) : Hint {
    override val color: Color
        get() = Color.BLACK
}
