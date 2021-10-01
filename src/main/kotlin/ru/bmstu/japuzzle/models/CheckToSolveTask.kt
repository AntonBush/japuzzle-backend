package ru.bmstu.japuzzle.models

import java.awt.Color

class CheckToSolveTask(
    override val id: Long,
    override val user: User,
    override val gameField: GameField
) : Task {
    override val solved: Boolean
        get() = _solved
    override val hints: Hints by lazy { Hints(getSideHints(isRow = true), getSideHints(isRow = false)) }
    private var _solved: Boolean = false

    override fun check(solution: GameField): Boolean {
        _solved = gameField == solution
        return solved
    }

    private fun getSideHints(isRow: Boolean): List<List<Hint>> {
        val lines = if (isRow) gameField.height else gameField.width
        val lineLength = if (isRow) gameField.width else gameField.height
        return List(lines) { lineIndex ->
            val sideHints = ArrayList<Hint>()
            var count = 0
            var color: Color? = null
            for (cellIndex in 0 until lineLength) {
                val (i, j) = if (isRow) Pair(lineIndex, cellIndex) else Pair(cellIndex, lineIndex)
                when {
                    color == gameField.cells[i][j] -> {
                        if (color != null) {
                            ++count
                        }
                    }
                    color != gameField.cells[i][j] -> {
                        count = when {
                            color == null -> {
                                1
                            }
                            gameField.cells[i][j] == null -> {
                                sideHints.add(Hint(color, count))
                                0
                            }
                            else -> {
                                sideHints.add(Hint(color, count))
                                1
                            }
                        }
                        color = gameField.cells[i][j]
                    }
                }
            }
            if (count != 0) {
                sideHints.add(Hint(color!!, count))
            }
            return@List List(sideHints.size) { i -> sideHints[i] }
        }
    }
}