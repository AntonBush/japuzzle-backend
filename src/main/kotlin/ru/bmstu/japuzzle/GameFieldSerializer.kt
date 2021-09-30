package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import ru.bmstu.japuzzle.models.GameField

class GameFieldSerializer : JsonSerializer<GameField>() {
    override fun serialize(value: GameField?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        if (gen == null || value == null) {
            return
        }
        gen.writeStartObject()
        gen.writeNumberField("width", value.width)
        gen.writeNumberField("height", value.height)
        gen.writeFieldName("cells")
        gen.writeStartArray()
        for (i in 0 until value.height) {
            gen.writeStartArray()
            for (j in 0 until value.width) {
                val colorInt = value[i, j]
                if (colorInt != null) {
                    var color = (colorInt.rgb % (256 * 256 * 256)).toString(16).uppercase()
                    for (k in 1..(6 - color.length)) {
                        color = "0$color"
                    }
                    gen.writeString(color)
                } else {
                    gen.writeObject(null)
                }
            }
            gen.writeEndArray()
        }
        gen.writeEndArray()
        gen.writeEndObject()
    }
}