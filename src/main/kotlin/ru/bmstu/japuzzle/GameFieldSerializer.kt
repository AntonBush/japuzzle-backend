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
        Utils.serializeListList(value.cells, gen) { color, g ->
            if (color != null) {
                gen.writeString("#${Utils.rgbToHex(color.rgb)}")
            } else {
                gen.writeObject(null)
            }
        }
        gen.writeEndObject()
    }
}