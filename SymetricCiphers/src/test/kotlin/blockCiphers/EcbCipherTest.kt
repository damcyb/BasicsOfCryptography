package blockCiphers

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import utils.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EcbCipherTest {

    private lateinit var inputText: String
    private lateinit var ecbCipher: EcbCipher
    private val regularProcessPath: String = "./src/main/resources/output/regular/"
    private val modificationProcessPath: String = "./src/main/resources/output/modified/ecb/"

    @BeforeAll
    fun initSetup() {
        ecbCipher = EcbCipher(BLOCK_SIZE_IN_BITS)
        inputText = readFile("./src/main/resources/input/inputText.txt")

    }

    @Test
    fun `plain text after ecb encryption and decryption should be the same`() {
        val encryptedText = ecbCipher.encrypt(inputText)
        writeToFile(encryptedText, "${regularProcessPath}ecbEncryptedText.txt")
        val decryptedText = ecbCipher.decrypt(encryptedText)
        writeToFile(decryptedText, "${regularProcessPath}ecbDecryptedText.txt")
        assertTrue(inputText == decryptedText)
    }

    @Test
    fun `deleting whole block from encrypted message`() {
        val encryptedInput = readFile("$regularProcessPath/ecbEncryptedText.txt")
        val modifiedEncryptedText = encryptedInput.removeTargetBlock(0)
        val decryptedText = ecbCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}removedBlock.txt")
    }

    @Test
    fun `duplicating block in encrypted message`() {
        val encryptedInput = readFile("$regularProcessPath/ecbEncryptedText.txt")
        val modifiedEncryptedText = encryptedInput.duplicateTargetBlock(0)
        val decryptedText = ecbCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}duplicatedBlock.txt")
    }

    @Test
    fun `swapping blocks in encrypted message`() {
        val encryptedInput = readFile("$regularProcessPath/ecbEncryptedText.txt")
        val modifiedEncryptedText = encryptedInput.swapTargetBlocks(0, 1)
        val decryptedText = ecbCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}swappedBlocks.txt")
    }

    @Test
    fun `changing target bite in encrypted message`() {
        val encryptedInput = readFile("$regularProcessPath/ecbEncryptedText.txt")
        val modifiedEncryptedText = encryptedInput.changeBitValueInTargetBlock(3, 0)
        val decryptedText = ecbCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}changedBit.txt")
    }

    @Test
    fun `swapping target bits in encrypted message`() {
        val encryptedInput = readFile("$regularProcessPath/ecbEncryptedText.txt")
        val modifiedEncryptedText = encryptedInput.swapTargetBytes(1, 4, 0)
        val decryptedText = ecbCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}swappedBytes.txt")
    }

    @Test
    fun `deleting byte in block in encrypted message`() {
        val encryptedInput = readFile("$regularProcessPath/ecbEncryptedText.txt")
        val modifiedEncryptedText = encryptedInput.removeByteFromBlock(1, 0)
        val decryptedText = ecbCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}removedByte.txt")
    }
}