package checker

import between
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import utils.BBSAnalyzer
import java.math.BigDecimal

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BBSCheckerTest {

//    private val path: String = "./testSJ/encryptionKey1.txt"
    private val path: String = "./encodedText.txt"
    private lateinit var bbs: String
    private lateinit var bbsAnalyzer: BBSAnalyzer
    private lateinit var bbsChecker: BBSChecker

    @BeforeAll
    fun `setup`() {
        bbsAnalyzer = BBSAnalyzer()
        bbsChecker = BBSChecker(path)
        bbs = bbsChecker.readBBS
    }

    @Test
    fun `Test pojedynczych bitów`() {
        val ones = bbsAnalyzer.countOnes(bbs)
        val lowerBound: Double = 9725.0
        val higherBound: Double = 10275.0
        println("Liczba jedynek wynosi: $ones <$lowerBound; $higherBound>")
        assertTrue(ones.between(lowerBound, higherBound))
    }

    @Test
    fun `Test serii`() {
        val seriesOfZeros: Map<Int, Int> = bbsAnalyzer.countSeries(bbs, '0')
        val seriesOfOnes: Map<Int, Int> = bbsAnalyzer.countSeries(bbs, '1')
        seriesOfZeros.forEach {
            println("Liczba serii zer o długości ${it.key} wynosi: ${it.value}")
        }
        seriesOfOnes.forEach {
            println("Liczba serii jedynek o długości ${it.key} wynosi: ${it.value}")
        }

        SoftAssertions().apply {
            assertTrue((seriesOfZeros.getValue(1).between(2315.0, 2685.0)))
            assertTrue((seriesOfZeros.getValue(2).between(1114.0, 1386.0)))
            assertTrue((seriesOfZeros.getValue(3).between(527.0, 723.0)))
            assertTrue((seriesOfZeros.getValue(4).between(240.0, 384.0)))
            assertTrue((seriesOfZeros.getValue(5).between(103.0, 209.0)))
            assertTrue((seriesOfZeros.getValue(6).between(103.0, 209.0)))
            assertTrue((seriesOfOnes.getValue(1).between(2315.0, 2685.0)))
            assertTrue((seriesOfOnes.getValue(2).between(1114.0, 1386.0)))
            assertTrue((seriesOfOnes.getValue(3).between(527.0, 723.0)))
            assertTrue((seriesOfOnes.getValue(4).between(240.0, 384.0)))
            assertTrue((seriesOfOnes.getValue(5).between(103.0, 209.0)))
            assertTrue((seriesOfOnes.getValue(6).between(103.0, 209.0)))
        }.assertAll()
    }
    @Test
    fun `Test dlugiej serii`() {
        assertFalse(bbsAnalyzer.checkIfLongSeriesExists(bbs))
    }

    @Test
    fun `Test pokerowy`() {
        val lowerBound: Double = 2.16
        val higherBound: Double = 46.17
        val groupedPartitions = bbsAnalyzer.pokerize(bbs)
        val score: BigDecimal = bbsAnalyzer.calculatePokerScore(groupedPartitions)
        println("Wynik wynosi: $score <$lowerBound; $higherBound>")
        assertTrue(score.between(lowerBound, higherBound))
    }
}