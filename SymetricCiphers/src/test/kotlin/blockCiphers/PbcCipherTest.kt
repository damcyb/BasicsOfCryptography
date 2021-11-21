package blockCiphers

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import utils.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PbcCipherTest {

    private lateinit var inputText: String
    private lateinit var pbcCipher: PbcCipher
    private val regularProcessPath: String = "./src/main/resources/output/regular/"
    private val modificationProcessPath: String = "./src/main/resources/output/modified/pcb/"

    @BeforeAll
    fun initSetup() {
        pbcCipher = PbcCipher(BLOCK_SIZE_IN_BITS)
        inputText = readFile("./src/main/resources/input/inputText.txt")
    }

    @Test
    fun `plain text after pbc encryption and decryption should be the same`() {
        val encryptedText = pbcCipher.encrypt(inputText)
        writeToFile(encryptedText, "${regularProcessPath}pbcEncryptedText.txt")
        val decryptedText = pbcCipher.decrypt(encryptedText)
        writeToFile(decryptedText, "${regularProcessPath}pbcDecryptedText.txt")
        assertTrue(inputText == decryptedText)
    }

    @Test
    fun `deleting whole block from encrypted message`() {
        val encryptedInput = pbcCipher.encrypt(inputText)
        val modifiedEncryptedText = encryptedInput.removeTargetBlock(0)
        val decryptedText = pbcCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}removedBlock.txt")
    }

    @Test
    fun `duplicating block in encrypted message`() {
        val encryptedInput = pbcCipher.encrypt(inputText)
        val modifiedEncryptedText = encryptedInput.duplicateTargetBlock(0)
        val decryptedText = pbcCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}duplicatedBlock.txt")
    }

    @Test
    fun `swapping blocks in encrypted message`() {
        val encryptedInput = pbcCipher.encrypt(inputText)
        val modifiedEncryptedText = encryptedInput.swapTargetBlocks(0, 1)
        val decryptedText = pbcCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}swappedBlocks.txt")
    }

    @Test
    fun `changing target bite in encrypted message`() {
        val encryptedInput = pbcCipher.encrypt(inputText)
        val modifiedEncryptedText = encryptedInput.changeBitValueInTargetBlock(3, 0)
        val decryptedText = pbcCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}changedBit.txt")
    }

    @Test
    fun `swapping target bits in encrypted message`() {
        val encryptedInput = pbcCipher.encrypt(inputText)
        val modifiedEncryptedText = encryptedInput.swapTargetBytes(1, 4, 0)
        val decryptedText = pbcCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}swappedBytes.txt")
    }

    @Test
    fun `deleting byte in block in encrypted message`() {
        val encryptedInput = pbcCipher.encrypt(inputText)
        val modifiedEncryptedText = encryptedInput.removeByteFromBlock(1, 0)
        val decryptedText = pbcCipher.decrypt(modifiedEncryptedText)
        writeToFile(decryptedText, "${modificationProcessPath}removedByte.txt")
    }
}