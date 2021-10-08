package ru.bmstu.japuzzle

import java.awt.Color

fun Color.rgbToHex() = "#${ "%08X".format(this.rgb).substring(2) }"

fun Color(s: String): Color = Color(s.substring(1).toInt(16))