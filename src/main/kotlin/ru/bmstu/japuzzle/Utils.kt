package ru.bmstu.japuzzle

object Utils {
    fun rgbToHex(rgb: Int) = "%08X".format(rgb).substring(2)
}