package blockCiphers

import utils.KeyType
import utils.generateAesKey
import utils.generateDesKey
import utils.generateRsaKey
import java.security.Key
import java.security.KeyPair
import javax.crypto.Cipher
import javax.crypto.SecretKey

class AssignedCipher(private val length: Int) {

    var secretKey: SecretKey = generateKey(length, KeyType.AES)
    private val secretKeyPair: KeyPair = generateKeyPair(1024)

    fun generateKey(length: Int, keyType: KeyType): SecretKey {
        return when (keyType) {
            KeyType.AES -> generateAesKey(length)
            KeyType.DES -> generateDesKey(length)
            else -> throw IllegalArgumentException("Unsupported key type")
        }
    }

    private fun generateKeyPair(length: Int): KeyPair {
        return generateRsaKey(length)
    }

    fun encrypt(text: String, mode: String, keyType: KeyType): ByteArray {
        return if (keyType == KeyType.RSA) encryptAsymmetric(text, mode) else encryptSymmetric(text, mode)
    }

    fun decrypt(encrypted: ByteArray, mode: String, keyType: KeyType): String {
        return if (keyType == KeyType.RSA) decryptAsymmetric(encrypted, mode) else decryptSymmetric(encrypted, mode)
    }

    private fun encryptSymmetric(text: String, mode: String): ByteArray {
        val cipher: Cipher = Cipher.getInstance(mode)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(text.toByteArray())
    }

    private fun decryptSymmetric(encrypted: ByteArray, mode: String): String {
        val cipher: Cipher = Cipher.getInstance(mode)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val decryptedByteArray: ByteArray = cipher.doFinal(encrypted)
        return String(decryptedByteArray)
    }

    private fun encryptAsymmetric(text: String, mode: String): ByteArray {
        val cipher: Cipher = Cipher.getInstance(mode)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeyPair.public)
        return cipher.doFinal(text.toByteArray())
    }

    private fun decryptAsymmetric(encrypted: ByteArray, mode: String): String {
        val cipher: Cipher = Cipher.getInstance(mode)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeyPair.private)
        val decryptedByteArray: ByteArray = cipher.doFinal(encrypted)
        return String(decryptedByteArray)
    }
}