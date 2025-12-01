import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.io.File

fun readFileLines(day: Int): List<String> = File({}.javaClass.getResource("input_$day.txt")!!.path).readLines()

private fun readFileMatrix(day: Int): List<List<String>> =
    readFileLines(day).map { line -> line.split("").filter { it.isNotEmpty() } }

fun readFileCharMatrix(day: Int): List<List<Char>> =
    readFileMatrix(day).map { line -> line.map { it.first() } }

fun readFileIntMatrix(day: Int): List<List<Int>> =
    readFileMatrix(day).map { line -> line.map { it.toInt() } }

fun String.integers() = Regex("-?\\d+").findAll(this).map { it.value.toInt() }.toList()

fun String.longs() = Regex("-?\\d+").findAll(this).map { it.value.toLong() }.toList()

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

fun <A, B> List<A>.pmap(f: suspend (A) -> B): List<B> = runBlocking {
    map { async(Dispatchers.Default) { f(it) } }.awaitAll()
}
