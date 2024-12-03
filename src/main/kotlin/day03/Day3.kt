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
    val (_, finalState) = generateSequence(0 to State(0, false)) { (index, state) ->
        val nextState = when (val instruction = instructions[index].value) {
            "do()" -> {
                state.copy(disabled = false)
            }

            "don't()" -> {
                state.copy(disabled = true)
            }

            else -> if (state.disabled) {
                state
            } else {
                state.copy(result = state.result + multiplyInstruction(instruction))
            }
        }
        index + 1 to nextState
    }.take(instructions.size).last()
    return finalState.result
}

private fun multiplyInstruction(instruction: String): Int = instruction.integers().let { (a, b) -> a * b }

private data class State(val result: Int, val disabled: Boolean)
