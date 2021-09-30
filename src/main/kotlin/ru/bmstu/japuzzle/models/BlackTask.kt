package ru.bmstu.japuzzle.models

import ru.bmstu.japuzzle.Utils
import java.awt.Color

class BlackTask(
    override val id: Long,
    override val player: Player,
    blackGameField: BlackGameField
) : Task {
    override val solved: Boolean
        get() = _solved
    override val hints: Hints by lazy { BlackHints(getRowHints(), getColumnHints()) }
    override val gameField: GameField?
        get() = if (solved) _gameField else null
    private val _gameField: GameField = blackGameField
    private var _solved: Boolean = false

    init {
        if (!Utils.matrixInvariant(blackGameField)) {
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

        _solved = _gameField == solution
        return solved
    }

    private fun getRowHints(): List<List<BlackHint>> {
        return List(_gameField.size) { i ->
            val rowHints = ArrayList<BlackHint>()
            var count = 0
            for (j in 0.._gameField[i].lastIndex) {
                when {
                    _gameField[i][j] != null -> ++count
                    count != 0 -> {
                        rowHints.add(BlackHint(count))
                        count = 0
                    }
                }
            }
            if (count != 0) {
                rowHints.add(BlackHint(count))
                count = 0
            }
            return@List rowHints
        }
    }
    private fun getColumnHints(): List<List<BlackHint>> {
        return List(_gameField[0].size) { j ->
            val columnHints = ArrayList<BlackHint>()
            var count = 0
            for (i in 0.._gameField.lastIndex) {
                when {
                    _gameField[i][j] != null -> ++count
                    count != 0 -> {
                        columnHints.add(BlackHint(count))
                        count = 0
                    }
                }
            }
            if (count != 0) {
                columnHints.add(BlackHint(count))
                count = 0
            }
            return@List columnHints
        }
    }
}