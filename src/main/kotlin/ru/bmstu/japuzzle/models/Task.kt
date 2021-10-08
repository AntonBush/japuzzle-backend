package ru.bmstu.japuzzle.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import ru.bmstu.japuzzle.TaskSerializer
import java.awt.Color

@JsonSerialize(using = TaskSerializer::class)
open class Task(
    open val id: Long,
    open val user: User,
    gameField: GameField
) {
    open val gameField: GameField
        get() = _gameField
    protected val _gameField = gameField
    open val hints: Hints = Hints(getSideHints(isRow = true), getSideHints(isRow = false))
    open var solved: Boolean = false
        protected set

    open fun check(solution: GameField): Boolean {
        solved = solved || (solution == _gameField)
        return solved
    }

    protected fun getSideHints(isRow: Boolean): List<List<Hint>> {
        val lines = if (isRow) _gameField.height else _gameField.width
        val lineLength = if (isRow) _gameField.width else _gameField.height
        return List(lines) { lineIndex ->
            val sideHints = ArrayList<Hint>()
            var count = 0
            var color: Color? = null
            for (cellIndex in 0 until lineLength) {
                val (i, j) = if (isRow) Pair(lineIndex, cellIndex) else Pair(cellIndex, lineIndex)
                when {
                    color == _gameField.cells[i][j] -> {
                        if (color != _gameField.colors.backgroundColor) {
                            ++count
                        }
                    }
                    color != _gameField.cells[i][j] -> {
                        count = when {
                            color == null -> {
                                1
                            }
                            _gameField.cells[i][j] == _gameField.colors.backgroundColor -> {
                                sideHints.add(Hint(color, count))
                                0
                            }
                            else -> {
                                sideHints.add(Hint(color, count))
                                1
                            }
                        }
                        color = _gameField.cells[i][j]
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