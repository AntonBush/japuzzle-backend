package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import ru.bmstu.japuzzle.models.Task


class TaskSerializer : JsonSerializer<Task>() {
    override fun serialize(value: Task?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        if (gen == null || value == null) {
            return
        }
        gen.writeStartObject()
        gen.writeBooleanField("solved", value.solved)
        gen.writeObjectField("field", value.gameField)
        gen.writeObjectField("hints", value.hints)
        gen.writeEndObject()
    }
}
