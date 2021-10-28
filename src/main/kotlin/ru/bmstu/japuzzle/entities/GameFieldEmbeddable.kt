package ru.bmstu.japuzzle.entities

import ru.bmstu.japuzzle.Color
import ru.bmstu.japuzzle.models.gamefield.FieldColors
import ru.bmstu.japuzzle.models.gamefield.GameField
import ru.bmstu.japuzzle.rgbToHex
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Embeddable

@Embeddable
class GameFieldEmbeddable(
    @Column(nullable = false)
    val width: Int,

    @Column(nullable = false)
    val height: Int,

    @Column(nullable = false)
    val background: String,

    @ElementCollection
    @Column(nullable = false)
    val colors: Map<Int, String>,

    @ElementCollection
    @Column(nullable = false)
    val cells: Map<Int, String>
) {
    fun toGameField(): GameField {
        return GameField(
            width,
            height,
            FieldColors(
                listOf(Color(background)) + colors.values.map { s -> Color(s) }
            ),
            List(height) { i ->
                List(width) { j ->
                    Color(cells[i * width + j]!!)
                }
            }
        )
    }

    companion object {
        fun newInstance(gf: GameField): GameFieldEmbeddable {
            return GameFieldEmbeddable(
                gf.width,
                gf.height,
                gf.colors.backgroundColor.rgbToHex(),
                gf.colors.colors.mapIndexed { i, c ->
                    i to c.rgbToHex()
                }.toMap(),
                gf.cells.flatMapIndexed { i, l ->
                    l.toList().mapIndexed { j, c ->
                        (i * gf.width + j) to c.rgbToHex()
                    }
                }.toMap()
            )
        }
    }
}