package ru.bmstu.japuzzle.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import ru.bmstu.japuzzle.TaskSerializer
import java.awt.Color

@JsonSerialize(using = TaskSerializer::class)
abstract class Task(
    open val id: Long,
    open val user: User,
    open val gameField: GameField
) {
    open val hints: Hints = Hints(getSideHints(isRow = true), getSideHints(isRow = false))
    open var solved: Boolean = false
        protected set

    open fun check(solution: GameField): Boolean {
        solved = solved || (solution == gameField)
        return solved
    }

    protected fun getSideHints(isRow: Boolean): List<List<Hint>> {
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
                        if (color != gameField.colors.backgroundColor) {
                            ++count
                        }
                    }
                    color != gameField.cells[i][j] -> {
                        count = when {
                            color == null -> {
                                1
                            }
                            gameField.cells[i][j] == gameField.colors.backgroundColor -> {
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