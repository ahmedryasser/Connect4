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