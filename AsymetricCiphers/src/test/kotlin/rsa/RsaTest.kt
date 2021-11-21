package rsa

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import utils.readFile
import utils.writeToFile
import java.math.BigInteger
import java.util.*

internal class RsaTest {

    private val writePath: String = "./src/main/resources/output/"
    private val readPath: String = "./src/main/resources/input/"
    private val primeMaxLength: Int = 1024
    private val rsa: Rsa = Rsa(
        p = BigInteger.probablePrime(primeMaxLength, Random()),
        q = BigInteger.probablePrime(primeMaxLength, Random())
    )

    @Test
    fun `encryption and decryption text should return same string`() {
        val input = readFile(readPath + "input.txt" )
        val encrypted = rsa.encryptMessage(input.toByteArray())
        val decrypted = rsa.decryptMessage(encrypted)
        val output = String(decrypted)
        writeToFile(output, writePath +"output.txt")
        assertTrue(input == output)
    }

    @Test
    fun `signVerifyTest`() {
        val input = readFile(readPath + "input.txt" )
        val signed = rsa.sign(input.toByteArray())
        val verified = rsa.verify(signed)
        val output = String(verified)
        writeToFile(output, writePath +"outputSign.txt")
        assertTrue(input == output)
    }
}