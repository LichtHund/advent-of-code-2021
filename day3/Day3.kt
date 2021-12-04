package dev.triumphteam

import java.io.File

val List<Int>.min: Int?
    get() {
        val count = groupingBy { it }.eachCount()
        val countValues = count.values
        if (countValues.first() == countValues.last()) return 0
        return count.minByOrNull { it.value }?.key
    }

val List<Int>.max: Int?
    get() {
        val count = groupingBy { it }.eachCount()
        val countValues = count.values
        if (countValues.first() == countValues.last()) return 1
        return count.maxByOrNull { it.value }?.key
    }

val String.flippedBits: String
    get() = map { if (it == '0') '1' else '0' }.joinToString("")

fun main() {
    val input = File("input.txt").readLines()

    fun part1(input: List<String>): Int {
        fun mapInputToMap(input: List<String>): Map<Int, List<Int>> {
            val bits = mutableMapOf<Int, MutableList<Int>>()

            input.map { it.toCharArray().map(Char::digitToInt).toList() }.forEach { list ->
                list.forEachIndexed { i, value ->
                    bits.computeIfAbsent(i) { mutableListOf() }.add(value)
                }
            }

            return bits
        }
      
      
        val gama = mapInputToMap(input).map { it.value.max }.joinToString("")
        return gama.toInt(2) * gama.flippedBits.toInt(2)
    }

    fun part2(input: List<String>): Int {

        fun filterIndexed(list: List<String>, index: Int = 0, max: Boolean = true): List<String> {
            if (list.size <= 1 || index >= list.first().length) return list
            val result = list.map { it.toCharArray().map(Char::digitToInt).toList() }.flatMap { values ->
                values.mapIndexedNotNull { i, value ->
                    if (i != index) return@mapIndexedNotNull null
                    value
                }
            }
            val compare = if (max) result.max else result.min
            return filterIndexed(list.filter { it[index].digitToInt() == compare }, index + 1, max)
        }

        return filterIndexed(input).first().toInt(2) * filterIndexed(input, max = false).first().toInt(2)
    }

    println(part1(input))
    println(part2(input))
}
