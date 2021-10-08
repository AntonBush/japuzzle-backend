package ru.bmstu.japuzzle.models

import java.awt.Color

class RandomBlackAndWightGameField(
    width: Int,
    height: Int,
) : RandomGameField(
    width,
    height,
    FieldColors(listOf(Color.WHITE, Color.BLACK))
)