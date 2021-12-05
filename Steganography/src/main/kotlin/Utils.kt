fun String.toEightBits(): String = this.padStart(8, '0')
fun Int.isEven(): Boolean = this % 2 == 0
fun Int.isOdd(): Boolean = this % 2 == 1
fun Int.getLSB(): Int = this % 2

fun String.fromBinaryStringToByteArray(): ByteArray {
    val c = this.chunked(BITS_IN_SINGLE_CHAR)
    return c.map {
        it.fromBinaryStringToInt().toByte()
    }.toByteArray()
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