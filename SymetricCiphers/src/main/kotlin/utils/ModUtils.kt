package utils

import java.util.*

fun String.removeTargetBlock(number: Int): String {
    val blocks: List<String> = this.chunked(BLOCK_SIZE_IN_BITS)
    require(blocks.size - 1 >= number)
        {"Target block is apart from encrypted message. Number should be between 0 and ${blocks.size - 1}"}
    return blocks
        .filterIndexed { index, _ -> index != number }
        .joinToString { it }
        .replace(", ", "")
}

fun String.duplicateTargetBlock(number: Int): String {
    val blocks: List<String> = this.chunked(BLOCK_SIZE_IN_BITS)
    require(blocks.size - 1 >= number)
        {"Target block is apart from encrypted message. Number should be between 0 and ${blocks.size - 1}"}
    return blocks
        .mapIndexed { index, s -> if (index == number) "$s$s" else s }
        .joinToString { it }
        .replace(", ", "")
}

fun String.swapTargetBlocks(blockOneNumber: Int, blockTwoNumber: Int): String {
    val blocks: MutableList<String> = this.chunked(BLOCK_SIZE_IN_BITS).toMutableList()
    require(blocks.size - 1 >= blockOneNumber)
        {"Block one is apart from encrypted message. Number should be between 0 and ${blocks.size - 1}"}
    require(blocks.size - 1 >= blockTwoNumber)
        {"Block two is apart from encrypted message. Number should be between 0 and ${blocks.size - 1}"}
    Collections.swap(blocks, blockOneNumber, blockTwoNumber)
    return blocks
        .joinToString { it }
        .replace(", ", "")
}

fun String.changeBitValueInTargetBlock(bitNumber: Int, blockNumber: Int): String {
    val blocks: List<String> = this.chunked(BLOCK_SIZE_IN_BITS)
    require(bitNumber <= 127) {"Bit should be between 0 and 127 inclusive"}
    require(blocks.size - 1 >= blockNumber)
        {"Block two is apart from encrypted message. Number should be between 0 and ${blocks.size - 1}"}
    val position: Int = BLOCK_SIZE_IN_BITS * blockNumber + bitNumber
    val value = (if (this[position] == '0') '1' else '0').toString()
    return StringBuilder(this)
        .replace(position, position + 1, value)
        .toString()
}

fun String.swapTargetBytes(byteOneNumber: Int, byteTwoNumber: Int, blockNumber: Int): String {
    val blocks: List<String> = this.chunked(BLOCK_SIZE_IN_BITS)
    require(byteOneNumber <= 15) {"Bit should be between 0 and 15 inclusive"}
    require(byteTwoNumber <= 15) {"Bit should be between 0 and 15 inclusive"}
    require(blocks.size - 1 >= blockNumber)
        {"Block two is apart from encrypted message. Number should be between 0 and ${blocks.size - 1}"}
    val valueOne = blocks[blockNumber].substring(byteOneNumber * 8, (byteOneNumber + 1) * 8)
    val valueTwo = blocks[blockNumber].substring(byteTwoNumber * 8, (byteTwoNumber + 1) * 8)
    return blocks
        .mapIndexed { index, s ->
            (if (index == blockNumber) StringBuilder(s).swap(byteOneNumber, byteTwoNumber, valueOne, valueTwo) else s).toString()
        }
        .joinToString { it }
        .replace(", ", "")
}

fun StringBuilder.swap(positionOne: Int, positionTwo: Int, valueOne: String, valueTwo: String): StringBuilder {
    return this.replace(positionOne * 8, (positionOne + 1) * 8, valueTwo)
        .replace(positionTwo * 8, (positionTwo + 1) * 8, valueOne)
}

fun String.removeByteFromBlock(byteNumber: Int, blockNumber: Int): String {
    val blocks: List<String> = this.chunked(BLOCK_SIZE_IN_BITS)
    require(byteNumber <= 15) {"Bit should be between 0 and 15 inclusive"}
    require(blocks.size - 1 >= blockNumber)
        {"Block two is apart from encrypted message. Number should be between 0 and ${blocks.size - 1}"}
    val startPosition = byteNumber * 8
    val endPosition = (byteNumber + 1) * 8
    return blocks
        .mapIndexed { index, s ->
            (if (index == blockNumber) StringBuilder(s).removeRange(startPosition, endPosition) else s).toString()
        }
        .joinToString { it }
        .replace(", ", "")
}
