package com.csclub.connect4.androidApp

interface ConnectFourAI {
    fun getNextMove(board: ConnectFourModel, player: Player): Int
}

class DummyAI : ConnectFourAI {
    override fun getNextMove(board: ConnectFourModel, player: Player): Int {
        for (i in 0 until NUM_COLUMNS) {
            if (!board.isColumnFull(i)) {
                return i
            }
        }
        throw IllegalStateException("Tried to get a move for a board that was already full.")
    }
}

class MinMaxAI : ConnectFourAI {
    companion object {
        private const val MAX_DEPTH = 6
    }

    override fun getNextMove(board: ConnectFourModel, player: Player): Int {
        val possibleMoves = Array(NUM_COLUMNS) { i ->
            val score = getMoveScore(board, player, i, MAX_DEPTH)
            if (score == 1.0) {
                return i
            }
            score
        }
        val maxScore = possibleMoves.maxByOrNull { it ?: -2.0 }
        val maximizingMoves = mutableListOf<Int>()
        for ((i, possibleMove) in possibleMoves.withIndex()) {
            if (possibleMove == maxScore) {
                maximizingMoves.add(i)
            }
        }
        return try {
            maximizingMoves.random()
        } catch (e: NoSuchElementException) {
            throw IllegalStateException("Tried to get a move for a board that was already full.")
        }
    }

    // Score of 1 means guaranteed win, score of -1 means guaranteed loss against perfect player
    // Score in between means that position is likely to win (high) or lose (low)
    // null means move is not valid (column is already full)
    private fun getMoveScore(board: ConnectFourModel, player: Player, column: Int, depth: Int): Double? {
        val boardCopy = board.clone()
        return if (boardCopy.isColumnFull(column)) {
            null
        } else {
            when (boardCopy.dropDisc(player, column)) {
                Victory.YES -> 1.0
                Victory.TIE -> 0.0
                else -> {
                    if (depth > 0) {
                        // If we still have time left to search, do so recursively
                        val possibleMoves = Array(NUM_COLUMNS) { i ->
                            val opponentScore = getMoveScore(
                                boardCopy,
                                if (player == Player.ONE) Player.TWO else Player.ONE,
                                i,
                                depth - 1
                            )
                            if (opponentScore != null) {
                                val moveScore = -opponentScore
                                if (moveScore == -1.0) {
                                    return moveScore
                                }
                                moveScore
                            } else {
                                null
                            }
                        }
                        possibleMoves.minByOrNull { it ?: 2.0 }
                    } else {
                        // If we're out of time, make a guess based on the state of the board
                        evaluateBoardScore(boardCopy, player, column)
                    }
                }
            }
        }
    }

    // Make a guess of whether this move is good based on how many of our discs it connects and opponent's discs it blocks
    private fun evaluateBoardScore(board: ConnectFourModel, player: Player, column: Int): Double? {
        fun getRow(board: ConnectFourModel, column: Int): Int {
            for (i in (0 until NUM_ROWS).reversed()) {
                if (board[column, i] != Player.NONE) {
                    return i + 1
                }
            }
            return 0
        }
        val bottomEmptyRow = getRow(board, column)
        if (bottomEmptyRow == NUM_ROWS) {
            return null
        } else {
            val playerTypeForDirection = arrayOf(
                board[column - 1, bottomEmptyRow],
                board[column - 1, bottomEmptyRow + 1],
                board[column - 1, bottomEmptyRow - 1],
                board[column + 1, bottomEmptyRow],
                board[column + 1, bottomEmptyRow + 1],
                board[column + 1, bottomEmptyRow - 1],
                board[column, bottomEmptyRow + 1],
                board[column, bottomEmptyRow - 1],
            )
            fun getScoreInDirection(incrementCols: Int, incrementRows: Int, playerType: Player): Int {
                var total = 0
                for (i in 1 until NUM_TO_WIN) {
                    if (playerType != board[column + incrementCols * i, bottomEmptyRow + incrementRows * i]) {
                        return total
                    } else {
                        total += i * i
                    }
                }
                return total
            }
            val scoreForDirection = IntArray(8) { i ->
                if (playerTypeForDirection[i] != Player.NONE) {
                    getScoreInDirection((i / 3).let {
                        when (it) {
                            0 -> -1
                            1 -> 1
                            else -> 0
                        }
                    }, (i % 3).let {
                        when (it) {
                            0 -> -1
                            1 -> 1
                            else -> 0
                        }
                    }, player)
                } else {
                    0
                }
            }
            return scoreForDirection.sum() / (14 * 8).toDouble()
        }
    }
}