package ru.bmstu.japuzzle.models.gamefield

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import ru.bmstu.japuzzle.GameFieldDeserializer
import ru.bmstu.japuzzle.GameFieldSerializer
import ru.bmstu.japuzzle.models.hints.Hint
import ru.bmstu.japuzzle.models.hints.Hints
import ru.bmstu.japuzzle.seamcarving.*
import java.awt.Color
import java.awt.image.BufferedImage

@JsonSerialize(using = GameFieldSerializer::class)
@JsonDeserialize(using = GameFieldDeserializer::class)
open class GameField(
    open val width: Int,
    open val height: Int,
    open val colors: FieldColors,
    open val cells: List<List<Color>>
) {
    open val hints = Hints(getSideHints(isRow = true), getSideHints(isRow = false))

    override operator fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (other !is GameField) {
            return false
        }
        return cells == other.cells
    }

    override fun hashCode(): Int {
        return cells.hashCode()
    }

    private fun getSideHints(isRow: Boolean): List<List<Hint>> {
        val lines = if (isRow) height else width
        val lineLength = if (isRow) width else height
        return List(lines) { lineIndex ->
            val sideHints = ArrayList<Hint>()
            var count = 0
            var color: Color? = null
            for (cellIndex in 0 until lineLength) {
                val (i, j) = if (isRow) Pair(lineIndex, cellIndex) else Pair(cellIndex, lineIndex)
                when {
                    color == cells[i][j] -> {
                        if (color != colors.backgroundColor) {
                            ++count
                        }
                    }
                    color != cells[i][j] -> {
                        count = when {
                            color == null -> {
                                if (cells[i][j] == colors.backgroundColor) {
                                    0
                                } else {
                                    1
                                }
                            }
                            color == colors.backgroundColor -> {
                                1
                            }
                            cells[i][j] == colors.backgroundColor -> {
                                sideHints.add(Hint(color, count))
                                0
                            }
                            else -> {
                                sideHints.add(Hint(color, count))
                                1
                            }
                        }
                        color = cells[i][j]
                    }
                }
            }
            if (count != 0) {
                sideHints.add(Hint(color!!, count))
            }
            return@List List(sideHints.size) { i -> sideHints[i] }
        }
    }

    companion object {
        fun from(image: BufferedImage, width: Int, height: Int, colors: FieldColors): GameField {
            val resizedImage = resizeImage(image, width, height)
            val pixels = IntArray(resizedImage.width * resizedImage.height)
            resizedImage.getRGB(0, 0, resizedImage.width, resizedImage.height, pixels, 0, resizedImage.width)

            val intensityMatrix = intensityMatrixOf(
                energyMatrixOf(
                    colorMatrixOf(
                        intMatrixOf(
                            image.width, image.height, pixels
                        )
                    )
                )
            )

            val field = Matrix(width, height) { i, j ->
                return@Matrix if (intensityMatrix[i][j] / 255.0 < 0.5) {
                    colors.backgroundColor
                } else {
                    colors.colors.first()
                }
            }

            return GameField(
                width,
                height,
                colors,
                field
            )
        }
    }
}