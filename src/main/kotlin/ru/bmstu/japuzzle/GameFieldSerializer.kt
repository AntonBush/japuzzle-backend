package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import ru.bmstu.japuzzle.models.GameField

class GameFieldSerializer : JsonSerializer<GameField>() {
    override fun serialize(value: GameField?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        serialize(value, gen, serializers, leaveCells = true)
    }

    companion object {
        fun serialize(value: GameField?, gen: JsonGenerator?, serializers: SerializerProvider?, leaveCells: Boolean) {
            if (gen == null) {
                return
            }
            if (value == null) {
                gen.writeObject(value)
                return
            }
            gen.writeStartObject()
            gen.writeNumberField("width", value.width)
            gen.writeNumberField("height", value.height)
            gen.writeFieldName("cells")
            if (leaveCells) {
                gen.writeStartArray()
                for (i in 0..value.cells.lastIndex) {
                    gen.writeStartArray()
                    for (j in 0..value.cells[i].lastIndex) {
                        val color = value.cells[i][j]
                        if (color != null) {
                            gen.writeString("#${color.rgbToHex().substring(2)}")
                        } else {
                            gen.writeObject(color)
                        }
                    }
                    gen.writeEndArray()
                }
                gen.writeEndArray()

            } else {
                gen.writeObject(null)
            }
            gen.writeEndObject()
        }
    }
}