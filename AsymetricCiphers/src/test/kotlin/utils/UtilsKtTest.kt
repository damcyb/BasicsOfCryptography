package utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class UtilsKtTest {

    @Test
    fun `should return true if passed number is prime`() {
        val input: Int = 7
        assertTrue(input.isPrime())
    }

    @Test
    fun `should return false if passed number is not prime`() {
        val input: Int = 9
        assertFalse(input.isPrime())
    }

    @Test
    fun `should return list of primes from specified range`() {
        val primes = generatePrimes(1, 50)
        assertThat(primes).containsOnly(
            f(2), f(3), f(5),
            f(7), f(11), f(13),
            f(17), f(19), f(23),
            f(29), f(31), f(37),
            f(41), f(43), f(47))
    }

    private fun f(i: Int): BigInteger {
        return BigInteger(i.toString())
    }
}