package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import ru.bmstu.japuzzle.models.Hints

class HintsSerializer : JsonSerializer<Hints>() {
    override fun serialize(value: Hints?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        if (gen == null || value == null) {
            return
        }
        gen.writeStartObject()
        gen.writeFieldName("rows")
        gen.writeStartArray()
        for (list in value.rows) {
            gen.writeStartArray()
            for (hint in list) {
                gen.writeObject(hint)
            }
            gen.writeEndArray()
        }
        gen.writeEndArray()

        gen.writeFieldName("columns")
        gen.writeStartArray()
        for (list in value.columns) {
            gen.writeStartArray()
            for (hint in list) {
                gen.writeObject(hint)
            }
            gen.writeEndArray()
        }
        gen.writeEndArray()
        gen.writeEndObject()
    }
}