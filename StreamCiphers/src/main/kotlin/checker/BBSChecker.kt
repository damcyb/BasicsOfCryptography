package checker

import readFile

class BBSChecker(private val path: String) {
    val readBBS: String = readFile(path)
}