package ru.bmstu.japuzzle.models

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