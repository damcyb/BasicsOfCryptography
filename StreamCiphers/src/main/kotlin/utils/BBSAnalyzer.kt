package utils

import java.math.BigDecimal

class BBSAnalyzer {
    fun countOnes(bbs: String): Int = bbs.count { c: Char ->  c == '1'}

    fun countSeries(bbs: String, character: Char): Map<Int, Int> {
        val occurrencesMap: MutableMap<Int, Int> = initializeOccurrencesMap()
        var lastFoundChar: Char = 'x'
        var counter: Int = 0
        var position: Int = 0
        bbs.forEach { char: Char ->
            if ((char == lastFoundChar) and (position != bbs.length - 1)) {
                counter++
            } else if ((char == lastFoundChar) and (position == bbs.length - 1)) {
                val flattenCounter = flatCounter(++counter)
                if (lastFoundChar == character) {
                    incrementOccurrencesMap(occurrencesMap, flattenCounter)
                }
                counter = 0
            } else if ((char != lastFoundChar) and (position == bbs.length - 1)) {
                val flattenCounter = flatCounter(counter)
                if (lastFoundChar == character) {
                    incrementOccurrencesMap(occurrencesMap, flattenCounter)
                }
                counter = 1
                if (char == character) {
                    incrementOccurrencesMap(occurrencesMap, counter)
                }
            } else {
                val flattenCounter = flatCounter(counter)
                if ((position != 0) and (lastFoundChar == character)) {
                    incrementOccurrencesMap(occurrencesMap, flattenCounter)
                }
                lastFoundChar = char
                counter = 1
            }
            position++
        }
        return occurrencesMap
    }

    fun checkIfLongSeriesExists(bbs: String): Boolean {
        var counter: Int = 0
        var lastFoundChar: Char = 'x'
        bbs.forEach { char -> Char
            counter = if (lastFoundChar == char) counter++ else 0
        }
        return counter >= 26
    }

    fun pokerize(bbs: String): Map<String, Int> {
        val partitions: List<String> = divideStringIntoPartitions(bbs)
        return groupPartitions(partitions)
    }

    fun calculatePokerScore(partitions: Map<String, Int>): BigDecimal {
        val fraction = BigDecimal(16).divide(BigDecimal(5000))
        return partitions.values
            .sumOf { Math.pow(it.toDouble(), 2.0) }.toBigDecimal()
            .multiply(fraction)
            .minus(BigDecimal(5000))
    }

    private fun divideStringIntoPartitions(input: String): List<String> = input.chunked(4)

    private fun groupPartitions(partitions: List<String>): Map<String, Int> =
        partitions
            .groupBy { it }
            .mapValues { it.value.size }

    private fun flatCounter(counter: Int): Int = if (counter < 6) counter else 6

    private fun incrementOccurrencesMap(occurrencesMap: MutableMap<Int, Int>, counter: Int) {
        occurrencesMap.merge(counter, 1, Int::plus)
    }

    private fun initializeOccurrencesMap() = mutableMapOf(
        1 to 0,
        2 to 0,
        3 to 0,
        4 to 0,
        5 to 0,
        6 to 0
    )
}