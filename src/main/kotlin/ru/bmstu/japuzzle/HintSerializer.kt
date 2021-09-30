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
        var color = (value.color.rgb % (256 * 256 * 256)).toString(16).uppercase()
        for (i in 1..(6 - color.length)) {
            color = "0$color"
        }
        gen.writeStringField("color", "#${color}")
        gen.writeNumberField("count", value.count)
        gen.writeEndObject()
    }
}