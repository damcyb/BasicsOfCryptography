package blockCiphers

import utils.*
import javax.crypto.Cipher
import javax.crypto.SecretKey


class EcbCipher(private val length: Int): GenericCipher {

    val secretKey: SecretKey = generateKey(length)

    override fun generateKey(length: Int): SecretKey {
        return generateAesKey(length)
    }

    override fun encrypt(text: String): String {
        text.validateAlphabet()
        val cipher: Cipher = Cipher.getInstance("AES/ECB/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val chunkedInput: List<String> = text.chunked(BLOCK_SIZE_IN_BITS / BITS_IN_BYTE)
        val cipherTextList: List<ByteArray> = chunkedInput.map { chunk ->
            val fulfilledBlock: ByteArray = chunk.toByteArray().fulfillBlock()
            cipher.doFinal(fulfilledBlock)
        }
        val cipherText: ByteArray = cipherTextList.join()
        return cipherText.toBinaryString()
    }

    override fun decrypt(encryptedText: String): String {
        val cipher = Cipher.getInstance("AES/ECB/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val chunkedEncryptedText = encryptedText.chunked(BLOCK_SIZE_IN_BITS)
        return chunkedEncryptedText.map { chunk ->
            val decryptedByteArray: ByteArray = cipher.doFinal(chunk.fromBinaryStringToByteArray())
            String(decryptedByteArray.removePaddingFromBlock())
        }.joinToString { it }
            .replace(", ", "")
    }

    fun encrypt(input: ByteArray): String {
        val cipher: Cipher = Cipher.getInstance("AES/ECB/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val cipherText: ByteArray = cipher.doFinal(input)
        return cipherText.toBinaryString()
    }

    fun decryptWithIv(encryptedText: String): ByteArray {
        val cipher: Cipher = Cipher.getInstance("AES/ECB/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        return cipher.doFinal(encryptedText.fromBinaryStringToByteArray())
    }
}