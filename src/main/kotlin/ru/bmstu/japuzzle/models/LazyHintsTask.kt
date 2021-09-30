package ru.bmstu.japuzzle.models

import java.awt.Color

class LazyHintsTask(
    gameField: GameField
) : Task {
    override val solved: Boolean
        get() = _solved
    override val hints: Hints by lazy { Hints(getSideHints(isRow = true), getSideHints(isRow = false)) }
    override val gameField: GameField?
        get() = if (solved) _gameField else null
    private val _gameField: GameField = gameField
    private var _solved: Boolean = false

    override fun check(solution: GameField): Boolean {
        _solved = _gameField == solution
        return solved
    }

    private fun getSideHints(isRow: Boolean): List<List<Hint>> {
        val lines = if (isRow) _gameField.height else _gameField.width
        val lineLength = if (isRow) _gameField.width else _gameField.height
        return List(lines) { lineIndex ->
            val sideHints = ArrayList<Hint>()
            var count = 0
            for (cellIndex in 0 until lineLength) {
                val (i, j) = if (isRow) Pair(lineIndex, cellIndex) else Pair(cellIndex, lineIndex)
                when {
                    _gameField[i, j] != null -> ++count
                    count != 0 -> {
                        sideHints.add(Hint(Color.BLACK, count))
                        count = 0
                    }
                }
            }
            if (count != 0) {
                sideHints.add(Hint(Color.BLACK, count))
            }
            return@List List(sideHints.size) { i -> sideHints[i] }
        }
    }
}