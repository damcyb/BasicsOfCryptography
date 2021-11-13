package utils

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ModUtilsKtTest {

    private lateinit var input: String

    @BeforeEach
    fun `setup`() {
        //osemeczkaaaaaaaasiodemeczkaaaaaa
        input =
            "01101111011100110110010101101101011001010110001101111010011010110110000101100001011000010110000101100001011000010110000101100001" +
            "01110011011010010110111101100100011001010110110101100101011000110111101001101011011000010110000101100001011000010110000101100001"
    }

    @Test
    fun `after removing block should return string without that block`() {
        val modifiedBlock = input.removeTargetBlock(0)
        assertThat(modifiedBlock).hasSize(128)
        assertTrue(
            modifiedBlock == "01110011011010010110111101100100011001010110110101100101011000110111101001101011011000010110000101100001011000010110000101100001"
        )
    }

    @Test
    fun `removing block outside the string should return exception`() {
        assertThatThrownBy {
            input.removeTargetBlock(2)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("Target block is apart from encrypted message")
    }

    @Test
    fun `after duplicating target block should return string with doubled block`() {
        val modifiedBlock = input.duplicateTargetBlock(0)
        assertThat(modifiedBlock).hasSize(128 * 3)
        assertTrue(modifiedBlock ==
                "01101111011100110110010101101101011001010110001101111010011010110110000101100001011000010110000101100001011000010110000101100001" +
                "01101111011100110110010101101101011001010110001101111010011010110110000101100001011000010110000101100001011000010110000101100001" +
                "01110011011010010110111101100100011001010110110101100101011000110111101001101011011000010110000101100001011000010110000101100001"
        )
    }

    @Test
    fun `after swapping target blocks should return string with swapped blocks`() {
        val modifiedBlock = input.swapTargetBlocks(0, 1)
        assertThat(modifiedBlock).hasSize(128 * 2)
        assertTrue(modifiedBlock ==
                "01110011011010010110111101100100011001010110110101100101011000110111101001101011011000010110000101100001011000010110000101100001" +
                "01101111011100110110010101101101011001010110001101111010011010110110000101100001011000010110000101100001011000010110000101100001"
        )
    }

    @Test
    fun `after change target bit should return string with swapped bit`() {
        val modifiedBlock = input.changeBitValueInTargetBlock(3, 1)
        assertThat(modifiedBlock).hasSize(128 * 2)
        assertTrue(modifiedBlock ==
                "01101111011100110110010101101101011001010110001101111010011010110110000101100001011000010110000101100001011000010110000101100001" +
                "01100011011010010110111101100100011001010110110101100101011000110111101001101011011000010110000101100001011000010110000101100001"
        )
    }

    @Test
    fun `after swapping target bits in block should return string with swapped bits`() {
        val modifiedBlock = input.swapTargetBytes(0, 1, 1)
        assertThat(modifiedBlock).hasSize(128 * 2)
        assertTrue(modifiedBlock ==
                "01101111011100110110010101101101011001010110001101111010011010110110000101100001011000010110000101100001011000010110000101100001" +
                "01101001011100110110111101100100011001010110110101100101011000110111101001101011011000010110000101100001011000010110000101100001"
        )
    }

    @Test
    fun `after removing byte of block should return string without that byte`() {
        val modifiedBlock = input.removeByteFromBlock(1, 0)
        assertThat(modifiedBlock).hasSize(128 * 2 - 8)
        assertTrue(modifiedBlock ==
                "011011110110010101101101011001010110001101111010011010110110000101100001011000010110000101100001011000010110000101100001" +
                "01110011011010010110111101100100011001010110110101100101011000110111101001101011011000010110000101100001011000010110000101100001"
        )
    }
}