package image

import RGB_IMAGE_TYPE
import fromBinaryStringToByteArray
import getLSB
import isEven
import isOdd
import toEightBits
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class ImageHandler(private val path: String) {

    val image: BufferedImage = ImageIO.read(File(path))
    val colorMatrix: List<Triple<Int, Int, Int>> = readImage()

    fun readImage(): List<Triple<Int, Int, Int>> {
        val matrix: ArrayList<Triple<Int, Int, Int>> = arrayListOf()
        var pixel: Triple<Int, Int, Int>
        for (h in 0 until image.height) {
            for (w in 0 until image.width) {
                pixel = pixelToRgb(h, w)
                matrix.add(pixel)
            }
        }
        return matrix.toList()
    }

    fun writeImage(colorMatrix: List<Triple<Int, Int, Int>>, path: String) {
        val bufferedImage: BufferedImage = BufferedImage(image.width, image.height, RGB_IMAGE_TYPE)
        var counterWidth: Int = 0
        var counterHeight: Int = 0
        colorMatrix.forEach { triple ->
            bufferedImage.setRGB(counterHeight, counterWidth, rgbToPixel(triple))

            if (shouldMoveToNextRow(counterWidth)) {
                counterWidth = 0
                counterHeight++
            } else {
                counterWidth++
            }
        }
        ImageIO.write(bufferedImage, "png", File(path))
    }

    fun encode(input: String): List<Triple<Int, Int, Int>> {
        val inputByteArray = ("$input#").toByteArray() // # as termination mark
        val encodedPixels: ArrayList<Triple<Int, Int, Int>> = arrayListOf()
        var counter: Int = 0
        val range: Int = 3
        inputByteArray.forEach {
            if (counter + range >= colorMatrix.size) throw IllegalArgumentException("Too long input message")
            val binaryRepresentationOfSingleCharacter: String = Integer.toBinaryString(it.toInt()).toEightBits()
            val threePixels: List<Triple<Int, Int, Int>> = colorMatrix.subList(counter, counter + range)
            val modifiedPixels: List<Triple<Int, Int, Int>> = modifyPixels(threePixels, binaryRepresentationOfSingleCharacter)
            with(encodedPixels) {
                add(modifiedPixels[0])
                add(modifiedPixels[1])
                add(modifiedPixels[2])
            }
            counter += 3
        }
        encodedPixels.addAll(colorMatrix.subList(counter, colorMatrix.lastIndex + 1))
        return encodedPixels.toList()
    }

    fun decode(colorMatrix: List<Triple<Int, Int, Int>>): String {
        val lessSignificantBits: String = getAllLessSignificantBits(colorMatrix)
        val filteredNoises = lessSignificantBits.filterIndexed { index, _ -> index % 9 != 8 }
        val byteArray = filteredNoises.fromBinaryStringToByteArray()
        return String(byteArray).substringBefore("#")
    }

    private fun getAllLessSignificantBits(colorMatrix: List<Triple<Int, Int, Int>>): String {
        return colorMatrix.joinToString { triple ->
            triple.first.getLSB().toString() + triple.second.getLSB().toString() + triple.third.getLSB().toString()
        }.replace(", ", "")
    }

    fun modifyPixels(threePixels: List<Triple<Int, Int, Int>>, binaryRepresentationOfSingleCharacter: String): List<Triple<Int, Int, Int>> {
        val modifiedPixels: ArrayList<Triple<Int, Int, Int>> = arrayListOf()
        threePixels.forEachIndexed { index, pixel ->
            val red: Int = encodePixelColor(pixel.first, binaryRepresentationOfSingleCharacter[3 * index])
            val green: Int = encodePixelColor(pixel.second, binaryRepresentationOfSingleCharacter[3 * index + 1])
            val blue: Int = if (isNinthBit(3 * index + 2)) {
                encodePixelColor(pixel.third, '0')
            } else {
                encodePixelColor(pixel.third, binaryRepresentationOfSingleCharacter[3*index + 2])
            }
            modifiedPixels.add(Triple(red, green, blue))
        }
        return modifiedPixels
    }

    fun encodePixelColor(pixelColor: Int, char: Char): Int {
        val charBitValue: Int = Character.getNumericValue(char)
        if (pixelColor.isEven() && charBitValue == 1) return pixelColor + 1
        if (pixelColor.isEven() && charBitValue == 0) return pixelColor
        if (pixelColor.isOdd() && charBitValue == 1) return pixelColor
        if (pixelColor.isOdd() && charBitValue == 0) return pixelColor - 1
        else throw IllegalArgumentException("Illegal char argument")
    }

    private fun isNinthBit(index: Int) = index >= 8

    private fun shouldMoveToNextRow(counterWidth: Int) = counterWidth == image.width - 1

    private fun rgbToPixel(pixel: Triple<Int, Int, Int>): Int = 65536 * pixel.first + 256 * pixel.second + pixel.third

    private fun pixelToRgb(heightCoordinate: Int, widthCoordinate: Int): Triple<Int, Int, Int> {
        val pixel = image.getRGB(heightCoordinate, widthCoordinate)
        val color = Color(pixel, true)
        return Triple(color.red, color.green, color.blue)
    }
}
