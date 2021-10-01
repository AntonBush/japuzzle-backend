package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import ru.bmstu.japuzzle.models.Hints
import ru.bmstu.japuzzle.Utils

class HintsSerializer : JsonSerializer<Hints>() {
    override fun serialize(value: Hints?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        if (gen == null || value == null) {
            return
        }
        gen.writeStartObject()
        gen.writeObjectField("rows", value.rows)
        gen.writeObjectField("columns", value.columns)
        gen.writeEndObject()
    }
}