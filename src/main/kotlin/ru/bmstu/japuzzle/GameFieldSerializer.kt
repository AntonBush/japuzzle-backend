package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import ru.bmstu.japuzzle.models.GameField

class GameFieldSerializer : JsonSerializer<GameField>() {
    override fun serialize(value: GameField?, gen: JsonGenerator?, serializers: SerializerProvider?) {
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

        gen.writeFieldName("background-color")
        gen.writeString(value.colors.backgroundColor.rgbToHex())
        gen.writeFieldName("colors")
        gen.writeStartArray()
        value.colors.colors.forEach { color ->
            gen.writeString(color.rgbToHex())
        }
        gen.writeEndArray()

        gen.writeFieldName("cells")
        gen.writeStartArray()
        value.cells.forEach { row ->
            gen.writeStartArray()
            row.forEach { color ->
                gen.writeString(color.rgbToHex())
            }
            gen.writeEndArray()
        }
        gen.writeEndArray()
        gen.writeEndObject()
    }
}