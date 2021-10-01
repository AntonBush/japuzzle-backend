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
        gen.writeStartObject()
        gen.writeNumberField("width", value.gameField.width)
        gen.writeNumberField("height", value.gameField.height)
        val cells = if (value.solved) {
            value.gameField.cells
        } else {
            null
        }
        gen.writeObjectField("cells", cells)
        gen.writeEndObject()

        gen.writeObjectField("hints", value.hints)
        gen.writeEndObject()
    }
}
