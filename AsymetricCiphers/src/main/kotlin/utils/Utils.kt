package utils

import java.io.File
import java.math.BigInteger
import java.util.*
import kotlin.math.sqrt

fun BigInteger.generateRelativePrimeNumber(): BigInteger {
    var randomNumber: Int
    val primes: MutableList<BigInteger> = generatePrimes(60_000, 100_000).toMutableList()
    while (primes.isNotEmpty()) {
        randomNumber = Random().nextInt(primes.size)
        if (primes[randomNumber].gcd(this).equals(BigInteger.ONE)) {
            return primes[randomNumber]
        } else {
            primes.remove(primes[randomNumber])
        }
    }
    throw IllegalArgumentException("Random number with GCD = 1 cannot be found")
}

fun generatePrimes(start: Int, end: Int): List<BigInteger> {
    val startMin = if (start < 2) 2 else start
    val primeList: MutableList<BigInteger> = mutableListOf()
    for (i in startMin..end) {
        if (i.isPrime()) primeList.add(i.toBigInteger())
    }
    return primeList
}

fun Int.isPrime(): Boolean {
    require(this > 1) {"Number has to be grater than 1"}
    var divisor: Int = 2
    while (divisor <= sqrt(this.toDouble())) {
        if (this % divisor == 0) {
            return false
        }
        divisor++
    }
    return true
}

fun generateComplementary(e: BigInteger, fi: BigInteger): BigInteger {
    val random: Random = Random()
    val x: BigInteger = random.nextInt(10).toBigInteger()
    return fi.multiply(x.add(BigInteger.ONE)).divide(e)
}

fun readFile(path: String, maxChars: Int? = null, minInputLength: Int = 10): String {
    val data = File(path).inputStream().readBytes().toString(Charsets.UTF_8)
    require(data.length >= minInputLength)
    return if (maxChars == null) data else data.dropLast(data.length - maxChars)
}

fun writeToFile(content: String, fileName: String, filePath: String = "./") {
    File("$filePath/$fileName").bufferedWriter().use { out -> out.write(content) }
}

private fun findGreatestCommonDivisor(n1: Int, n2: Int): Int {
    return if (n2 != 0)
        findGreatestCommonDivisor(n2, n1 % n2)
    else
        n1
}

fun ByteArray.byteArrayToString(): String =
    this.joinToString { it.toString() }
        .replace(", ", "")