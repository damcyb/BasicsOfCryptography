package generator

import areEqual
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import utils.BBSAnalyzer
import java.math.BigInteger

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SeriesTest {

    private lateinit var bbsAnalyzer: BBSAnalyzer
    private lateinit var bbsGenerator: BBSGenerator

    @BeforeAll
    fun `setup`() {
        bbsAnalyzer = BBSAnalyzer()
        bbsGenerator = BBSGenerator(BigInteger.ZERO, 10, BigInteger.ZERO) //unnecessary parameters
    }
    @Test
    fun `check test series number 1`() {
        val input: String = "0010111000000011"
        val seriesOfZeros: Map<Int, Int> = bbsAnalyzer.countSeries(input, '0')
        val seriesOfOnes: Map<Int, Int> = bbsAnalyzer.countSeries(input, '1')
        val expectedSeriesOfZeros: Map<Int, Int> = initMockSeries(1, 1, 0, 0, 0, 1)
        val expectedSeriesOfOnes: Map<Int, Int> = initMockSeries(1, 1, 1, 0, 0, 0)
        assertTrue(seriesOfZeros.areEqual(expectedSeriesOfZeros))
        assertTrue(seriesOfOnes.areEqual(expectedSeriesOfOnes))
    }


    @Test
    fun `check test series number 2`() {
        val input: String = "0010011"
        val seriesOfZeros: Map<Int, Int> = bbsAnalyzer.countSeries(input, '0')
        val seriesOfOnes: Map<Int, Int> = bbsAnalyzer.countSeries(input, '1')
        val expectedSeriesOfZeros: Map<Int, Int> = initMockSeries(0, 2, 0, 0, 0, 0)
        val expectedSeriesOfOnes: Map<Int, Int> = initMockSeries(1, 1, 0, 0, 0, 0)
        assertTrue(seriesOfZeros.areEqual(expectedSeriesOfZeros))
        assertTrue(seriesOfOnes.areEqual(expectedSeriesOfOnes))
    }

    @Test
    fun `check test series number 3`() {
        val input: String = "00100111111111111111110000111111"
        val seriesOfZeros: Map<Int, Int> = bbsAnalyzer.countSeries(input, '0')
        val seriesOfOnes: Map<Int, Int> = bbsAnalyzer.countSeries(input, '1')
        val expectedSeriesOfZeros: Map<Int, Int> = initMockSeries(0, 2, 0, 1, 0, 0)
        val expectedSeriesOfOnes: Map<Int, Int> = initMockSeries(1, 0, 0, 0, 0, 2)
        assertTrue(seriesOfZeros.areEqual(expectedSeriesOfZeros))
        assertTrue(seriesOfOnes.areEqual(expectedSeriesOfOnes))
    }

    @Test
    fun `check test series number 4`() {
        val input: String = "010011111111111111111000011111101110"
        val seriesOfZeros: Map<Int, Int> = bbsAnalyzer.countSeries(input, '0')
        val seriesOfOnes: Map<Int, Int> = bbsAnalyzer.countSeries(input, '1')
        val expectedSeriesOfZeros: Map<Int, Int> = initMockSeries(3, 1, 0, 1, 0, 0)
        val expectedSeriesOfOnes: Map<Int, Int> = initMockSeries(1, 0, 1, 0, 0, 2)
        assertTrue(seriesOfZeros.areEqual(expectedSeriesOfZeros))
        assertTrue(seriesOfOnes.areEqual(expectedSeriesOfOnes))
    }

    private fun initMockSeries(first: Int, second: Int, third: Int, fourth: Int, fifth: Int, sixth: Int): Map<Int, Int> {
        return mapOf(
            1 to first,
            2 to second,
            3 to third,
            4 to fourth,
            5 to fifth,
            6 to sixth
        )
    }
}