package day03

import integers
import println
import readFileLines

fun main() {
    val input = readFileLines(3).joinToString("")

    part1(input).println()
    part2(input).println()
}

private fun part1(input: String): Int =
    Regex("mul\\(\\d+,\\d+\\)").findAll(input).sumOf { multiplyInstruction(it.value) }

private fun part2(input: String): Int {
    val instructions = Regex("mul\\(\\d+,\\d+\\)|don't\\(\\)|do\\(\\)").findAll(input).toList()
    return instructions.fold(0 to false) { (result, disabled), it ->
        when (val instruction = it.value) {
            "do()" -> result to false

            "don't()" -> result to true

            else -> if (disabled) {
                result to true
            } else {
                (result + multiplyInstruction(instruction)) to false
            }
        }
    }.first
}

private fun multiplyInstruction(instruction: String): Int = instruction.integers().let { (a, b) -> a * b }
