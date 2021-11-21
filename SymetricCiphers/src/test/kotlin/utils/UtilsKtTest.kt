package utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.function.Predicate.isEqual

internal class UtilsKtTest {

    @Test
    fun test() {
        val input = byteArrayOf(15, 16)
        val result = input.fulfillBlock().toBinaryString()
        println(result)
        println(result.length)
    }

    @Test
    fun `should return from binary string integer in u2`() {
        val input = "11101011"
        val result = input.toIntFromU2()
        Assertions.assertEquals(-21, result)
    }

    @Test
    fun `should return from binary string byte array - negative input`() {
        val input = "11101011"
        val result = input.fromBinaryStringToByteArray()
        Assertions.assertEquals(byteArrayOf(-21)[0], result[0])
    }

    @Test
    fun `should return from binary string byte array - positive input`() {
        val input = "01000001"
        val result = input.fromBinaryStringToByteArray()
        Assertions.assertEquals(byteArrayOf(65)[0], result[0])
    }

    @Test
    fun `should return from binary string byte array - mixed input`() {
        val input = "1110101101000001"
        val result = input.fromBinaryStringToByteArray()
        Assertions.assertEquals(byteArrayOf(-21, 65)[0], result[0])
        Assertions.assertEquals(byteArrayOf(-21, 65)[1], result[1])
    }

    @Test
    fun `should return same ByteArray after toBinaryString and fromBinaryStringToByteArray`() {
        val input = byteArrayOf(-10, 11, 0, 15, -11, 120)
        val result = input.toBinaryString().fromBinaryStringToByteArray()
        input.forEachIndexed { index, byte ->
            Assertions.assertEquals(byte, result[index])
        }
    }

    @Test
    fun `should join byteArrays`() {
        val byteOne: ByteArray = "test".toByteArray()
        val byteTwo: ByteArray = "test".toByteArray()
        val result = listOf(byteOne, byteTwo).join()
        Assertions.assertEquals("testtest", String(result))
    }
}