package encoder

import toBinary
import xor

class StreamEncoder(private val bbs: String) {

//    fun encode(text: String): String {
//        val textByteArray: ByteArray = text.toByteArray()
//        val bbsByteArray: ByteArray = bbs.toByteArray()
//        require(textByteArray.size <= bbsByteArray.size) {
//            "Text byteArray cannot be longer than encoding key byteArray"
//        }
//        val encodedText: StringBuilder = StringBuilder()
//        var encodedBit: Byte
//        textByteArray.forEachIndexed { index, byte ->
//            encodedBit = byte.xor(bbsByteArray[index])
//            encodedText.append(encodedBit)
//        }
//        return encodedText.toString()
//    }
    fun encode(text: String): String {
        val textBinaryString: String = text.toBinary()

        require(textBinaryString.length <= bbs.length) {
            "Text byteArray cannot be longer than encoding key byteArray"
        }
        val encodedText: StringBuilder = StringBuilder()
        var encodedBit: Char
        textBinaryString.forEachIndexed { index, char ->
            encodedBit = char.xor(bbs[index])
            encodedText.append(encodedBit)
        }
//        println(encodedText.length)
        return encodedText.toString()
    }

    fun decode(encodedText: String): String {
        val decodedText: StringBuilder = StringBuilder()
        var decodedEightBits: StringBuilder = StringBuilder()
        var buffer: StringBuilder = StringBuilder()
        var decodedChar: Char

        encodedText.forEachIndexed { index, char -> buffer.append(char.xor(bbs[index])) }

        for(i in encodedText.indices step 8) {
            decodedEightBits.append(buffer.substring(i, i+8))
            decodedChar = convertBinaryToDecimal(decodedEightBits.toString().toLong()).toChar()
            decodedText.append(decodedChar)
            decodedEightBits.clear()
        }
        return decodedText.toString()
    }

    private fun convertBinaryToDecimal(num: Long): Int {
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
}