package day04

import println
import readFileCharMatrix

fun main() {
    val input = readFileCharMatrix(4)

    part1(input).println()
    part2(input).println()
}

private fun part1(input: List<List<Char>>): Int {
    val directions = listOf(1 to 0, 1 to 1, 1 to -1, 0 to 0, 0 to 1, 0 to -1, -1 to 0, -1 to 1, -1 to -1)
    return input.sumIndexed { rowIndex, colIndex ->
        directions.count { direction -> findWord("XMAS", input, rowIndex, colIndex, direction) }
    }
}

private fun part2(input: List<List<Char>>): Int = input.sumIndexed { rowIndex, colIndex ->
    val validFirstDiagonal = findWord("MAS", input, rowIndex, colIndex, 1 to 1) ||
            findWord("SAM", input, rowIndex, colIndex, 1 to 1)
    val validSecondDiagonal = findWord("MAS", input, rowIndex, colIndex + 2, 1 to -1) ||
            findWord("SAM", input, rowIndex, colIndex + 2, 1 to -1)

    if (validFirstDiagonal && validSecondDiagonal) {
        1
    } else {
        0
    }
}

private fun List<List<Char>>.sumIndexed(operation: (rowIndex: Int, colIndex: Int) -> Int): Int =
    foldIndexed(0) { rowIndex, accRow, row ->
        accRow + row.foldIndexed(0) { colIndex, acc, value ->
            acc + operation(rowIndex, colIndex)
        }
    }

private fun findWord(word: String, input: List<List<Char>>, row: Int, col: Int, direction: Pair<Int, Int>): Boolean {
    val (dx, dy) = direction
    val isValid = row in input.indices && col in input[row].indices && row + dx * (word.length - 1) in input.indices
            && col + dy * (word.length - 1) in input[row].indices
    return isValid && (0..(word.length - 1)).map { input[row + (dx * it)][col + (dy * it)] }.joinToString("") == word
}
