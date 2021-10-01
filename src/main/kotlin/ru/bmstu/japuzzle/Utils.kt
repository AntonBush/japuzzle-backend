package ru.bmstu.japuzzle

import com.fasterxml.jackson.core.JsonGenerator

object Utils {
    fun <E> serializeList(
        list: List<E>?,
        gen: JsonGenerator,
        serializer: (E, JsonGenerator) -> Unit = { element, g -> g.writeObject(element) }
    ) {
        if (list == null) {
            gen.writeObject(list)
            return
        }
        gen.writeStartArray()
        for (element in list) {
            serializer(element, gen)
        }
        gen.writeEndArray()
    }

    fun <E> serializeListList(
        listList: List<List<E>>?,
        gen: JsonGenerator,
        serializer: (E, JsonGenerator) -> Unit = { element, g -> g.writeObject(element) }
    ) {
        if (listList == null) {
            gen.writeObject(listList)
            return
        }
        gen.writeStartArray()
        for (list in listList) {
            serializeList(list, gen, serializer)
        }
        gen.writeEndArray()
    }

    fun rgbToHex(rgb: Int) = "%08X".format(rgb).substring(2)
}