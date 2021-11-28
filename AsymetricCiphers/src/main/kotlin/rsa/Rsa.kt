package rsa

import utils.generateRelativePrimeNumber
import java.math.BigInteger

class Rsa(
    private val p: BigInteger,
    private val q: BigInteger

) {
    private val n: BigInteger = p.multiply(q)
    private val fi: BigInteger = ((p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE))))
    private val e: BigInteger = fi.generateRelativePrimeNumber()
    private val d: BigInteger = e.modInverse(fi)

    init {
        require(e.gcd(fi).equals(BigInteger.ONE)) {"Gcd for e and fi is not equal 1"}
        require((e.multiply(d)).mod(fi).equals(BigInteger.ONE)) {"$e and $d are not complementary. Fi: $fi"}
    }

    fun sign(message: ByteArray): List<ByteArray> {
        return message.map {
            BigInteger(byteArrayOf(it))
                .modPow(d, n)
                .toByteArray()
        }
    }

    fun verify(message: List<ByteArray>): ByteArray {
        return message.map {
            BigInteger(it)
                .modPow(e, n)
                .toByteArray()
                .toList()
        }.flatten().toByteArray()
    }

    fun encryptMessage(message: ByteArray): List<ByteArray> {
        return message.map {
            BigInteger(byteArrayOf(it))
                .modPow(e, n)
                .toByteArray()
        }
    }

    fun decryptMessage(message: List<ByteArray>): ByteArray {
        return message.map {
            BigInteger(it)
                .modPow(d, n)
                .toByteArray()
                .toList()
        }.flatten().toByteArray()
    }

    fun printoutData() {
        println("p = $p, q = $q")
        println("fi = $fi")
        println("Private key: e = $e, n = $n")
        println("Public key: d = $d, n = $n")
    }
}