package dev.triumphteam

import java.io.File

fun main() {
    val input = File("/input.txt").readLines().map(String::toInt)
    part1(input)
    part2(input)
}

fun part1(input: List<Int>): Int {
    return input.mapIndexed() { i, value ->
        if (i == 0) return@mapIndexed 0
        if (value > input[i - 1]) 1 else 0
    }.sum()
}

fun part2(input: List<Int>): Int {
    return part1(input.windowed(3, 1).map(List<Int>::sum))
}
