package blockCiphers

import javax.crypto.SecretKey

interface GenericCipher {
    fun generateKey(length: Int): SecretKey
    fun encrypt(text: String): String
    fun decrypt(encryptedText: String): String
}