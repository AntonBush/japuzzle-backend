package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import ru.bmstu.japuzzle.models.FieldColors
import ru.bmstu.japuzzle.models.GameField
import java.awt.Color

class GameFieldDeserializer : JsonDeserializer<GameField>() {
    override fun deserialize(jp: JsonParser?, ctxt: DeserializationContext?): GameField {
        if (jp == null) {
            throw NullPointerException("json parser is null")
        }
        val cells = mutableListOf<List<Color>>()
        val colors = mutableSetOf<Color>()
        val solution = jp.codec.readTree<JsonNode>(jp).get("solution") as ArrayNode
        solution.forEach { rowNode ->
            val rowList = mutableListOf<Color>()
            val row = (rowNode as ArrayNode)
            row.forEach { colorNode ->
                val color = Color(colorNode.asText())
                rowList.add(color)
                colors.add(color)
            }
            cells.add(rowList)
        }

        return GameField(
            solution[0].size(),
            solution.size(),
            FieldColors(colors.toList()),
            cells
        )
    }

}
