package ru.bmstu.japuzzle.models

import java.awt.Color

class EmptyGameField(
    width: Int,
    height: Int,
    colors: FieldColors,
) : GameField(
    width,
    height,
    colors,
    List(height) { List(width) { colors.backgroundColor } }
)