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
        gen.writeFieldName("field")
        if (value.solved) {
            gen.writeObject(value.gameField)
        } else {
            gen.writeStartObject()
            gen.writeNumberField("width", value.gameField.width)
            gen.writeNumberField("height", value.gameField.height)
            gen.writeObjectField("cells", null)
            gen.writeEndObject()
        }
        gen.writeObjectField("hints", value.hints)
        gen.writeEndObject()
    }
}
