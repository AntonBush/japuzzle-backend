package ru.bmstu.japuzzle.models

import ru.bmstu.japuzzle.Utils
import java.awt.Color

data class BlackTask(
    override val id: Long,
    override val player: Player,
    private val gameField: List<List<BlackColor?>>
) : Task {
    override val solved: Boolean
        get() = _solved
    override val hints: Hints
        get() = TODO("Not yet implemented")
    private var _solved: Boolean = false

    init {
        if (gameField.isEmpty()) {
            throw IllegalArgumentException("Game field is empty")
        }
        if (!Utils.matrixInvariant(gameField)) {
            throw IllegalArgumentException("Matrix invariant is not respected")
        }
    }

    override fun check(solution: List<List<Color?>>): Boolean {
        if (solution.any { row ->
                row.any { cellColor ->
                    cellColor !is BlackColor?
                }
        }) {
            return false
        }

        TODO("Not yet implemented")
    }
}