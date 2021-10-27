package ru.bmstu.japuzzle.seamcarving

import java.awt.image.BufferedImage

fun resizeImage(image: BufferedImage, newWidth: Int, newHeight: Int): BufferedImage {
    val pixels = IntArray(image.width * image.height)
    image.getRGB(0, 0, image.width, image.height, pixels, 0, image.width)
    val oldRGBMatrix = intMatrixOf(image.width, image.height, pixels)
    val newRGBMatrix = resizeRGBMatrix(newWidth, newHeight, oldRGBMatrix)
    val newImage = BufferedImage(image.width - newWidth, image.height - newHeight, BufferedImage.TYPE_INT_ARGB)
    newImage.setRGB(0, 0, newImage.width, newImage.height,
        intArrayOf(newRGBMatrix), 0, newImage.width)

    return newImage
}
