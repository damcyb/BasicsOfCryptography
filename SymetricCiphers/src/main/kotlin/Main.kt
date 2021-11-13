import blockCiphers.CbcCipher
import blockCiphers.EcbCipher
import blockCiphers.PbcCipher
import utils.BLOCK_SIZE_IN_BITS
import utils.toBinaryString

fun main() {
    val ecbCipher = EcbCipher(BLOCK_SIZE_IN_BITS)
    val cbcCipher = CbcCipher(BLOCK_SIZE_IN_BITS)
    val pbcCipher = PbcCipher(BLOCK_SIZE_IN_BITS)


}