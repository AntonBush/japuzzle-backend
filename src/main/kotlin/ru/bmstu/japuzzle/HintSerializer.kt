package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import ru.bmstu.japuzzle.models.Hint

class HintSerializer : JsonSerializer<Hint>() {
    override fun serialize(value: Hint?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        if (gen == null || value == null) {
            return
        }
        gen.writeStartObject()
        val hexColor = "#${Utils.rgbToHex(value.color.rgb)}"
        gen.writeStringField("color", hexColor)
        gen.writeNumberField("count", value.count)
        gen.writeEndObject()
    }
}