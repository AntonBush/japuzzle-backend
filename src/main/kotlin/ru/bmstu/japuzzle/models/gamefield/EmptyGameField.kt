package ru.bmstu.japuzzle.models.gamefield

import ru.bmstu.japuzzle.models.gamefield.FieldColors
import ru.bmstu.japuzzle.models.gamefield.GameField
import ru.bmstu.japuzzle.models.hints.Hints

class EmptyGameField(
    width: Int,
    height: Int,
    colors: FieldColors,
    override val hints: Hints
) : GameField(
    width,
    height,
    colors,
    List(height) { List(width) { colors.backgroundColor } }
)