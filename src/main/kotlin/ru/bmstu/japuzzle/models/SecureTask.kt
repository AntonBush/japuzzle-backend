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
        get() = if (solved || !initFinished) {
            super.gameField
        } else {
            emptyField
        }
    private val emptyField: GameField = EmptyGameField(
        gameField.width,
        gameField.height,
        gameField.colors,
        gameField.hints
    )
    /* Костыль основанный на системе типов котлин
     * Свойство emptyField до инициализации этого класса == null
     * Но в базовом классе свойство gameField используется,
     * поэтому там возникает NullPointerException
     * initFinished — Boolean, значение поумолчанию которого false
     */
    private val initFinished = true
}
