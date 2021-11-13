package encoder

import checker.BBSChecker
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import readFile
import writeToFile

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class StreamEncoderTest {

    private lateinit var bbs: String
    private val path: String = "./bbs_v1.txt"
    private val textPath: String = "./text.txt"
    private lateinit var bbsChecker: BBSChecker
    private lateinit var streamEncoder: StreamEncoder

    @BeforeAll
    fun `setup`() {
        bbsChecker = BBSChecker(path)
        bbs = bbsChecker.readBBS
        streamEncoder = StreamEncoder(bbs)
    }

    @Test
    fun `encodeTestText`() {
        val text: String = readFile(textPath, maxChars = 20_000.div(8))
        val encodedText = streamEncoder.encode(text)
        writeToFile(encodedText, "encodedText.txt", ".")
        val decodedText = streamEncoder.decode(encodedText)
        writeToFile(decodedText, "decodedText.txt", ".")
        assertTrue(decodedText == text)
    }
}