package utils

import java.io.File
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import kotlin.experimental.xor
import kotlin.math.pow


fun generateIv(blockLength: Int): IvParameterSpec {
    val iv = ByteArray(blockLength)
    SecureRandom().nextBytes(iv)
    return IvParameterSpec(iv)
}

fun generateAesKey(length: Int): SecretKey {
    val keyGenerator = KeyGenerator.getInstance("AES")
    keyGenerator.init(length)
    return keyGenerator.generateKey()
}

private fun String.fulfillToEight(): String {
    val padSize: Int = BITS_IN_BYTE - this.length
    var str: StringBuilder = StringBuilder()
    for(i in 1..padSize) {
        str.append("0")
    }
    str.append(this)
    return str.toString()
}

fun ByteArray.toBinaryString(): String {
    return this.joinToString {
        val binary: String = Integer.toBinaryString(it.toInt())
        if (it >= 0) binary.fulfillToEight() else binary.removeRange(0, 24)
    }.replace(", ", "")
}

fun String.fromBinaryStringToByteArray(): ByteArray {
    val c = this.chunked(BITS_IN_BYTE)
    return c.map {
        if (it.startsWith("0")) it.fromBinaryStringToInt().toByte() else it.toIntFromU2().toByte()
    }.toByteArray()
}

fun String.toIntFromU2(): Int {
    var result: Int = 2.0.pow(length - 1).times(-1).toInt()
    this.forEachIndexed { index: Int, char: Char ->
        if (index != 0 && (index != (length - 1) || char != '0')) {
            val exponent: Int = char.digitToInt()
            result += (2 * exponent).toDouble().pow(length - index - 1).toInt()
        }
    }
    return result
}

fun String.fromBinaryStringToInt(): Int {
    return if(this == "00000000") 0 else convertBinaryToDecimal(this.trimStart('0').toLong())
}

fun convertBinaryToDecimal(num: Long): Int {
    var num = num
    var decimalNumber = 0
    var i = 0
    var remainder: Long
    while (num.toInt() != 0) {
        remainder = num % 10
        num /= 10
        decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
        ++i
    }
    return decimalNumber
}

fun ByteArray.fulfillBlock(): ByteArray {
    val list: MutableList<Byte> = this.toMutableList()
    val elementsToAdd: Int = BLOCK_SIZE_IN_BYTES - this.size % (BLOCK_SIZE_IN_BYTES + 1) // 16 + 1 because 16 % 17 = 16
    list.addAll(List(elementsToAdd) { _ -> elementsToAdd.toByte()})
    return list.toByteArray()
}

fun ByteArray.removePaddingFromBlock(): ByteArray {
    val elementsToRemove: Int = this.last().toInt()
    val startIndex = this.size-1-elementsToRemove
    if (startIndex >= 0 && elementsToRemove >= 0) {
        for (i in this.size-elementsToRemove until this.size) {
            if (this[i].toInt() != elementsToRemove) {
                return this
            }
        }
        return this.dropLast(elementsToRemove).toByteArray()
    }
    return this
}

fun String.divideIntoBlocks(blockSizeInBits: Int): List<ByteArray> =
    this.chunked(blockSizeInBits / BITS_IN_BYTE).map { it.toByteArray() }

fun ByteArray.xor(iv: ByteArray): ByteArray {
    require(this.size == iv.size) { "text block must have same size as iv"}
    return this.mapIndexed { index, byte -> byte.xor(iv[index]) }.toByteArray()
}

fun readFile(path: String, maxChars: Int? = null, minInputLength: Int = 10): String {
    val data = File(path).inputStream().readBytes().toString(Charsets.UTF_8)
    require(data.length >= minInputLength)
    return if (maxChars == null) data else data.dropLast(data.length - maxChars)
}

fun writeToFile(content: String, fileName: String, filePath: String = "./") {
    File("$filePath/$fileName").bufferedWriter().use { out -> out.write(content) }
}

fun String.validateAlphabet() {
    this.forEach {
        require(ALPHABET.contains(it, ignoreCase = true)) {"Text contains not allowed char $it"}
    }
}

fun List<ByteArray>.join(): ByteArray {
    var accumulator: ByteArray = ByteArray(0)
    this.forEach { element ->
        accumulator = accumulator.plus(element)
    }
    return accumulator
}
