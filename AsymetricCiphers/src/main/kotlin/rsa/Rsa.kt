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

    fun encryptMessage(message: ByteArray): ByteArray {
        return BigInteger(message)
            .modPow(e, n)
            .toByteArray()
    }

    fun decryptMessage(message: ByteArray): ByteArray {
        return BigInteger(message)
            .modPow(d, n)
            .toByteArray()
    }

    fun sign(message: ByteArray): ByteArray {
        return decryptMessage(message) // decrypting plain text is equal to signing it
    }

    fun verify(message: ByteArray): ByteArray {
        return encryptMessage(message)
    }
}