package dev.triumphteam

import java.io.File

fun main() {
  
    val magicSquare = listOf(
        listOf(1, 7, 13, 19, 25),
        listOf(14, 20, 21, 2, 8),
        listOf(22, 3, 9, 15, 16),
        listOf(10, 11, 17, 23, 4),
        listOf(18, 24, 5, 6, 12)
    )

    val inputFile = File("input.txt").readLines()

    val drawNumbers = inputFile.first().split(",").map { it.toInt() }

    val boards = inputFile.subList(1, inputFile.size).filterNot { it.isEmpty() }.chunked(5).map { list ->
        list.map { it.split(" ").filterNot(String::isEmpty).map(String::toInt) }
    }

    fun part2(boards: MutableList<List<List<Int>>>): Int {
        val emptyBoards = boards.map { board -> board.map { line -> line.map { 0 }.toMutableList() } }.toMutableList()

        var lastWon = emptyList<List<Int>>()
        var lastDrawnIndex = 0
        var lastWinNumber = 0
        for (drawIndex in drawNumbers.indices) {
            val number = drawNumbers[drawIndex]
            val iterator = boards.iterator()
            val emptyIterator = emptyBoards.iterator()
            while (iterator.hasNext()) {
                val board = iterator.next()
                val emptyBoard = emptyIterator.next()

                for (j in board.indices) {
                    val line = board[j]
                    for (k in line.indices) {
                        val item = line[k]
                        if (item != number) continue

                        emptyBoard[j][k] = magicSquare[j][k]
                        if (hasWon(emptyBoard)) {
                            lastWon = board
                            lastDrawnIndex = drawIndex
                            lastWinNumber = number
                            iterator.remove()
                            emptyIterator.remove()
                        }
                    }
                }
            }
        }

        val drawnNumbers = drawNumbers.subList(0, lastDrawnIndex + 1)
        val nonMarked = lastWon.flatten().filterNot { it in drawnNumbers }
        return nonMarked.sum() * lastWinNumber
    }

    fun part1(boards: List<List<List<Int>>>): Int {
        val emptyBoards = boards.map { board -> board.map { line -> line.map { 0 }.toMutableList() } }

        for (drawIndex in drawNumbers.indices) {
            val number = drawNumbers[drawIndex]
            for (i in boards.indices) {
                val board = boards[i]

                for (j in board.indices) {
                    val line = board[j]
                    for (k in line.indices) {
                        val item = line[k]
                        if (item != number) continue

                        emptyBoards[i][j][k] = magicSquare[j][k]
                        if (!hasWon(emptyBoards[i])) continue
                        val drawnNumbers = drawNumbers.subList(0, drawIndex + 1)
                        val nonMarked = board.flatten().filterNot { it in drawnNumbers }
                        return nonMarked.sum() * number
                    }
                }
            }
        }

        return 0
    }

    println(part1(boards))
    println(part2(boards.toMutableList()))
}

fun hasWon(board: List<MutableList<Int>>): Boolean {
    fun checkHorizontal(board: List<MutableList<Int>>): Boolean {
        for (i in board.indices) {
            var sum = 0
            for (j in board[i].indices) sum += board[i][j]
            if (sum == 65) return true
        }

        return false
    }

    fun checkVertical(board: List<MutableList<Int>>): Boolean {
        for (i in board.indices) {
            var sum = 0
            for (j in board[i].indices) sum += board[j][i]
            if (sum == 65) return true
        }

        return false
    }

    return checkHorizontal(board) || checkVertical(board)
}
