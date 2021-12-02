package dev.triumphteam

import java.io.File

fun main() {
    val input = File("/input.txt").readLines()
    println(part1(input))
    println(part2(input))
}

fun part2(input: List<String>): Int {
    var position = 0
    var depth = 0
    var aim = 0

    for ((instruction, value) in input.toInstructions()) {
        when (instruction) {
            "forward" -> {
                position += value
                depth += aim * value
            }
            "up" -> aim -= value
            "down" -> aim += value
        }
    }

    return position * depth
}

fun part1(input: List<String>): Int {
    var position = 0
    var depth = 0

    for ((instruction, value) in input.toInstructions()) {
        when (instruction) {
            "forward" -> position += value
            "up" -> depth -= value
            "down" -> depth += value
        }
    }

    return position * depth
}

fun List<String>.toInstructions() = map { it.split(" ") }.map { it.first() to it.last().toInt() }
