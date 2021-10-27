package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import ru.bmstu.japuzzle.models.hints.Hint

class HintSerializer : JsonSerializer<Hint>() {
    override fun serialize(value: Hint?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        if (gen == null) {
            return
        }
        if (value == null) {
            gen.writeObject(value)
            return
        }
        gen.writeStartObject()
        gen.writeObjectField("color", value.color.rgbToHex())
        gen.writeNumberField("count", value.count)
        gen.writeEndObject()
    }
}