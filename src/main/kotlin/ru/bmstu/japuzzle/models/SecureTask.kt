package ru.bmstu.japuzzle.models

import java.awt.Color

class SecureTask(
    id: Long,
    user: User,
    gameField: GameField,
) : Task(
    id,
    user,
    gameField
) {
    override val gameField: GameField
        get() = if (solved) {
            super.gameField
        } else {
            emptyField
        }
    private val emptyField: GameField = EmptyGameField(
        gameField.width,
        gameField.height,
        gameField.colors)
}
