import generator.BBSGenerator
import java.math.BigInteger

fun main(args: Array<String>) {
    val bbsGenerator = BBSGenerator(BigInteger("789"), 100)
    val bbs = bbsGenerator.generate()
    println(bbs)
}