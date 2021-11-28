package blockCiphers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import utils.KeyType
import utils.generateRsaKey
import utils.readFile
import java.security.Provider
import java.security.Security
import java.util.*
import java.util.stream.Collectors
import kotlin.system.measureNanoTime


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AssignedCipherTest {

    private val length: Int = 256
    private val cipher: AssignedCipher = AssignedCipher(length)
    private val shortText: String = readFile("./src/main/resources/input/shortText.txt")
    private val mediumText: String = readFile("./src/main/resources/input/mediumText.txt")
    private val longText: String = readFile("./src/main/resources/input/longText.txt")

    @Test
    fun `measure execution time`() {

        // AES/ECB/PKCS5Padding
        doExperimentWithTargetAlgorithmAndText("AES/ECB/PKCS5Padding", shortText, KeyType.AES)
        doExperimentWithTargetAlgorithmAndText("AES/ECB/PKCS5Padding", mediumText, KeyType.AES)
        doExperimentWithTargetAlgorithmAndText("AES/ECB/PKCS5Padding", longText, KeyType.AES)

        // AES/CBC/PKCS5Padding
        doExperimentWithTargetAlgorithmAndText("AES/CBC/PKCS5Padding", shortText, KeyType.AES)
        doExperimentWithTargetAlgorithmAndText("AES/CBC/PKCS5Padding", mediumText, KeyType.AES)
        doExperimentWithTargetAlgorithmAndText("AES/CBC/PKCS5Padding", longText, KeyType.AES)

        // DES/ECB/PKCS5Padding
        cipher.secretKey = cipher.generateKey(56, KeyType.DES)
        doExperimentWithTargetAlgorithmAndText("DES/ECB/PKCS5Padding", shortText, KeyType.DES)
        doExperimentWithTargetAlgorithmAndText("DES/ECB/PKCS5Padding", mediumText, KeyType.DES)
        doExperimentWithTargetAlgorithmAndText("DES/ECB/PKCS5Padding", longText, KeyType.DES)

        // DES/CBC/PKCS5Padding
        doExperimentWithTargetAlgorithmAndText("DES/CBC/PKCS5Padding", shortText, KeyType.DES)
        doExperimentWithTargetAlgorithmAndText("DES/CBC/PKCS5Padding", mediumText, KeyType.DES)
        doExperimentWithTargetAlgorithmAndText("DES/CBC/PKCS5Padding", longText, KeyType.DES)

        // RSA/ECB/PKCS1Padding
        doExperimentWithTargetAlgorithmAndText("RSA/ECB/NoPadding", shortText, KeyType.RSA)
        doExperimentWithTargetAlgorithmAndText("RSA/ECB/NoPadding", mediumText, KeyType.RSA)
        doExperimentWithTargetAlgorithmAndText("RSA/ECB/NoPadding", longText, KeyType.RSA)

    }

    private fun doExperimentWithTargetAlgorithmAndText(mode: String, text: String, keyType: KeyType) {
        val encryptionFileTime = measureNanoTime { cipher.encrypt(text, mode, keyType) }
        val encrypted: ByteArray = cipher.encrypt(text, mode, keyType)
        val decryptionFileTime = measureNanoTime { cipher.decrypt(encrypted, mode, keyType) }
        printResults(mode, text.length, encryptionFileTime, decryptionFileTime)
    }

    private fun printResults(mode: String, length: Int, encryptionTime: Long, decryptionTime: Long) {
        println("Current mode: $mode, file length $length: ")
        println("Encryption time: $encryptionTime nanoseconds")
        println("Decryption time: $decryptionTime nanoseconds")
        println("---------------------------")
    }

    @Test
    fun asd () {
        val algorithms: List<String> = Arrays.stream(Security.getProviders())
            .flatMap { provider -> provider.getServices().stream() }
            .filter { service -> "Cipher" == service.getType() }
            .map(Provider.Service::getAlgorithm)
            .collect(Collectors.toList())
        println(algorithms)
    }
}