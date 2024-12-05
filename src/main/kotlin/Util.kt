import java.io.File

fun readFileLines(day: Int): List<String> = File({}.javaClass.getResource("input_$day.txt")!!.path).readLines()

private fun readFileMatrix(day: Int): List<List<String>> =
    readFileLines(day).map { line -> line.split("").filter { it.isNotEmpty() } }

fun readFileCharMatrix(day: Int): List<List<Char>> =
    readFileMatrix(day).map { line -> line.map { it.first() } }

fun String.integers() = Regex("-?\\d+").findAll(this).map { it.value.toInt() }.toList()

fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> =
    fold(listOf(emptyList())) { acc, element ->
        if (predicate(element)) {
            acc + listOf(emptyList())
        } else {
            acc.dropLast(1) + listOf(acc.last() + element)
        }
    }

fun <T> List<List<T>>.transpose(): List<List<T>> = List(first().size) { i -> List(size) { j -> this[j][i] } }

fun Any?.println() = println(this)
