package generator

import squared
import writeToFile
import java.math.BigInteger

class BBSGenerator(private val blumNumber: BigInteger, private val length: Int, random: BigInteger? = null) {

    private var randomNumber: BigInteger = random ?: generateRandomNumber()

    fun generate(): String {
        val result: StringBuilder = StringBuilder()
        var x: BigInteger = randomNumber.squared().mod(blumNumber)
        for (i in 1..length) {
            x = x.squared().mod(blumNumber)
            result.append((x.mod(BigInteger("2"))))
        }
        return result.toString()
    }

    private fun generateRandomNumber(): BigInteger {
        var randomNumber: Int
        var counter: Int = 10_000_000
        var greatestCommonDivisor: Int
        val numbers: MutableList<Int> = generateSequence {
            (counter--).takeIf { it > 1_000_000 }?.toInt()
        }.toMutableList()
        while (numbers.isNotEmpty()) {
            randomNumber = numbers.random()
            greatestCommonDivisor = findGreatestCommonDivisor(randomNumber, blumNumber.toInt())
            if (greatestCommonDivisor == 1) {
                return randomNumber.toBigInteger()
            } else {
                numbers.remove(randomNumber)
            }
        }
        throw IllegalArgumentException("Random number with GCD = 1 cannot be found")
    }

    fun writeBBSToFile(content: String, fileName: String, filePath: String = "./") {
        writeToFile(content, fileName, filePath)
    }

    private fun findGreatestCommonDivisor(n1: Int, n2: Int): Int {
        return if (n2 != 0)
            findGreatestCommonDivisor(n2, n1 % n2)
        else
            n1
    }
}