package ru.bmstu.japuzzle.models.gamefield

import java.awt.Color

class RandomBlackAndWightGameField(
    width: Int,
    height: Int,
) : RandomGameField(
    width,
    height,
    FieldColors(listOf(Color.WHITE, Color.BLACK))
)