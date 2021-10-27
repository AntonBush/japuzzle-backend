package ru.bmstu.japuzzle.models.gamefield

import java.awt.Color

class BlackAndWightRandomGameField(
    width: Int,
    height: Int,
) : RandomGameField(
    width,
    height,
    FieldColors(listOf(Color.WHITE, Color.BLACK))
)