package blockCiphers

import utils.*
import javax.crypto.SecretKey

class CbcCipher(private val length: Int): GenericCipher {

    private val iv: ByteArray = generateIv(length.div(BITS_IN_BYTE)).iv
    private val ecbCipher: EcbCipher = EcbCipher(length)

    override fun generateKey(length: Int): SecretKey {
        return generateAesKey(length)
    }

    override fun encrypt(text: String): String {
        text.validateAlphabet()
        var iv: ByteArray = iv
        val blocks: List<ByteArray> = text.divideIntoBlocks(length)
        val fulfilledBlocks = blocks.mapIndexed { index, bytes ->
            if (index == blocks.size - 1) bytes.fulfillBlock() else bytes
        }
        return fulfilledBlocks.joinToString { block ->
            val ivEncryptedBlock = block.xor(iv)
            val encryptedBlock = ecbCipher.encrypt(ivEncryptedBlock)
            iv = encryptedBlock.fromBinaryStringToByteArray()
            encryptedBlock
        }.replace(", ", "")
    }

    override fun decrypt(encryptedText: String): String {
        val blocks: List<String> = encryptedText.chunked(BLOCK_SIZE_IN_BITS)
        var iv: ByteArray = iv
        return blocks.joinToString { binaryString ->
            val decryptedBinaryBlock = ecbCipher.decryptWithIv(binaryString)
            val ivDecryptedBlock = decryptedBinaryBlock.xor(iv)
            iv = binaryString.fromBinaryStringToByteArray()
            String(ivDecryptedBlock.removePaddingFromBlock())
        }.replace(", ", "")
    }
}