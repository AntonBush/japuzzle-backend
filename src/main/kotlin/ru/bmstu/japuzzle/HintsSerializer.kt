package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import ru.bmstu.japuzzle.models.Hints

class HintsSerializer : JsonSerializer<Hints>() {
    override fun serialize(value: Hints?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        if (gen == null) {
            return
        }
        if (value == null) {
            gen.writeObject(value)
            return
        }
        gen.writeStartObject()
        gen.writeObjectField("rows", value.rows)
        gen.writeObjectField("columns", value.columns)
        gen.writeEndObject()
    }
}