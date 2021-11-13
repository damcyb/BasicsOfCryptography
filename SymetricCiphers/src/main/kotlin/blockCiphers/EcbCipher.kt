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
        val cipherText: ByteArray = cipher.doFinal(text.toByteArray())
        return cipherText.toBinaryString()
    }

    fun encrypt(input: ByteArray): String {
        val cipher: Cipher = Cipher.getInstance("AES/ECB/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val cipherText: ByteArray = cipher.doFinal(input)
        return cipherText.toBinaryString()
    }

    override fun decrypt(encryptedText: String): String {
        val cipher = Cipher.getInstance("AES/ECB/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedByteArray: ByteArray = cipher.doFinal(encryptedText.fromBinaryStringToByteArray())
        val decryptedText = String(decryptedByteArray)
//        decryptedText.validateAlphabet()
        return decryptedText
    }

    fun decryptWithIv(encryptedText: String): ByteArray {
        val cipher: Cipher = Cipher.getInstance("AES/ECB/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        return cipher.doFinal(encryptedText.fromBinaryStringToByteArray())
    }

}