import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.experimental.xor

fun BigDecimal.between(lowerBound: Double, higherBound: Double): Boolean {
    return (this >=lowerBound.toBigDecimal()) and (this <= higherBound.toBigDecimal())
}

fun Int.between(lowerBound: Double, higherBound: Double): Boolean {
    return (this >= lowerBound) and (this <= higherBound)
}

fun BigInteger.squared(): BigInteger = this.multiply(this)

fun <K, V>Map<K, V>.areEqual(second: Map<K, V>): Boolean {
    if (this.size != second.size) return false
    return this.all {
        it.value == second[it.key]
    }
}

fun readFile(path: String, maxChars: Int? = null, minInputLength: Int = 10): String {
    val data = File(path).inputStream().readBytes().toString(Charsets.UTF_8)
    require(data.length >= minInputLength)
    return if (maxChars == null) data else data.dropLast(data.length - maxChars)
}

fun writeToFile(content: String, fileName: String, filePath: String = "./") {
    File("$filePath/$fileName").bufferedWriter().use { out -> out.write(content) }
}

fun Char.xor(char: Char): Char {
    if (this != '1' && this != '0') throw IllegalArgumentException("Converted char must be 0 or 1")
    if (char != '1' && char != '0') throw IllegalArgumentException("Converted argument must be 0 or 1")
    return when {
        this == '1' && char == '1' -> '0'
        this == '0' && char == '0' -> '0'
        this == '1' && char == '0' -> '1'
        this == '0' && char == '1' -> '1'
        else -> throw IllegalStateException()
    }
}

fun String.toBinary(): String {
    val binaryString = StringBuilder()
    var binaryChar: String
    var fulfilledBinary: String
    this.forEach { char: Char ->
        binaryChar = Integer.toBinaryString(char.toInt())
        fulfilledBinary = if (binaryChar.length <= 8) binaryChar.fulfillToEight() else binaryChar
        binaryString.append(fulfilledBinary)
    }
    return binaryString.toString()
}

private fun String.fulfillToEight(): String {
    val padSize: Int = 8 - this.length
    var str: StringBuilder = StringBuilder()
    for(i in 1..padSize) {
        str.append("0")
    }
    str.append(this)
    return str.toString()
}


