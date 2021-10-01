package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import ru.bmstu.japuzzle.models.GameField
import ru.bmstu.japuzzle.models.Task


class TaskSerializer : JsonSerializer<Task>() {
    override fun serialize(value: Task?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        if (gen == null) {
            return
        }
        if (value == null) {
            gen.writeObject(value)
            return
        }
        gen.writeStartObject()
        gen.writeBooleanField("solved", value.solved)

        gen.writeFieldName("field")
        GameFieldSerializer.serialize(value.gameField, gen, serializers, value.solved)

        gen.writeObjectField("hints", value.hints)
        gen.writeEndObject()
    }
}
