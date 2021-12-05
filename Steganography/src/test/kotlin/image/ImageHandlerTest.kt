package image

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import toEightBits

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ImageHandlerTest {

    val imageHandler = ImageHandler("./src/main/resources/panda.png")

    @Test
    fun `decoded message should be the same as input message`() {
        //given
        val inputMessage = "Testowa wiadomosc nie moze byc za dluga, poniewaz moze zabraknac pikseli do jej zakodowania."
        //when
        val encodedMessage = imageHandler.encode(inputMessage)
        val decodedMessage = imageHandler.decode(encodedMessage)
        //then
        assertEquals(inputMessage, decodedMessage)
    }


    @Test
    fun `should properly encoded pixel color with given char`() {
        //given
        val char0: Char = '0'
        val char1: Char = '1'
        val pixelEven: Int = 240
        val pixelOdd: Int = 255
        //when
        val f1 = imageHandler.encodePixelColor(pixelEven, char0)
        val f2 = imageHandler.encodePixelColor(pixelEven, char1)
        val f3 = imageHandler.encodePixelColor(pixelOdd, char0)
        val f4 = imageHandler.encodePixelColor(pixelOdd, char1)
        //then
        assertEquals(240, f1)
        assertEquals(241, f2)
        assertEquals(254, f3)
        assertEquals(255, f4)
    }

    @Test
    fun `pixels with binary representation of character should return modified pixels after modify fun`() {
        //given
        val threePixels = listOf(
            Triple(43, 205, 24),
            Triple(171, 140, 41),
            Triple(40, 231, 90),
        )
        val binaryRepresentation = "01000011"
        //when
        val modifiedPixels = imageHandler.modifyPixels(threePixels, binaryRepresentation)
        //then
        assertEquals(modifiedPixels[0], Triple(42, 205, 24))
        assertEquals(modifiedPixels[1], Triple(170, 140, 40))
        assertEquals(modifiedPixels[2], Triple(41, 231, 90))
    }

    @Test
    fun `should pad binary representation to 8 chars length`() {
        val repr: String = "1000"
        val e = repr.toEightBits()
        assertTrue(e.length == 8)
    }

    @Test
    fun `should properly decode triple`() {
        val input: List<Triple<Int, Int, Int>> = listOf(
            Triple(240, 255, 231),
            Triple(240, 250, 230),
            Triple(240, 211, 231)
        )
        val decoded = imageHandler.decode(input)
        assertEquals("a", decoded)
    }
}