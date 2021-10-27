package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
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
        gen.writeNumberField("id", value.id)
        gen.writeStringField("user", value.user.name)
        gen.writeBooleanField("solved", value.solved)

        gen.writeObjectField("field", value.gameField)

        gen.writeObjectField("hints", value.gameField.hints)
        gen.writeEndObject()
    }
}
