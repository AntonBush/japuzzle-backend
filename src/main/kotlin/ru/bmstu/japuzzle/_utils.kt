package ru.bmstu.japuzzle

import java.awt.Color

fun Color.rgbToHex() = "%08X".format(this.rgb)