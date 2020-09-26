package com.csclub.connect4.androidApp

import kotlin.experimental.and
import kotlin.experimental.or

const val NUM_COLUMNS = 7
const val NUM_ROWS = 6

/* Represents a Connect Four board with indices as follows:
    (0,NUM_ROWS),   (1,NUM_ROWS),   ...,    (NUM_COLUMNS,NUM_ROWS)
    ...
    (0,1),          (1,1),          ...,    (NUM_COLUMNS,1)
    (0,0),          (1,0),          ...,    (NUM_COLUMNS,0)
 */
class ConnectFourModel private constructor(private val p1Discs: ByteArray, private val p2Discs: ByteArray) : ConnectFourModelReadonly {
    constructor() : this(ByteArray(NUM_COLUMNS) { 0.toByte()}, ByteArray(NUM_COLUMNS) { 0.toByte() })

    enum class Player { ONE, TWO, NONE }
    enum class Victory { YES, NO, TIE}

    fun dropDisc(player: Player, column: Int): Victory {
        for (i in (0 until NUM_ROWS).reversed()) {
            val mask = (1 shl i).toByte()
            if (p1Discs[column] and mask == 0.toByte() && p2Discs[column] and mask == 0.toByte()) {
                val playerArray = (if (player == Player.ONE) p1Discs else p2Discs)
                playerArray[column] = playerArray[column] or mask
                return isWinningMove(player, column, i)
            }
        }
        throw IllegalStateException("Tried to add a disc to a column that was already full!")
    }

    override operator fun get(column: Int, row: Int): Player {
        val mask = (1 shl row).toByte()
        return when {
            p1Discs[column] and mask > 0.toByte() -> Player.ONE
            p2Discs[column] and mask > 0.toByte() -> Player.TWO
            else -> Player.NONE
        }
    }

    override fun clone(): ConnectFourModel = ConnectFourModel(p1Discs.copyOf(), p2Discs.copyOf())

    private fun isWinningMove(player: Player, column: Int, row: Int) : Victory {
        // TODO
        // Returns Victory.YES if the player who just dropped a disc into position (column, row) has won
        // Returns Victory.TIE if the board is full but the player did not win
        // Returns Victory.NO otherwise
        return Victory.NO
    }
}

interface ConnectFourModelReadonly {
    operator fun get(column: Int, row: Int): ConnectFourModel.Player

    fun clone(): ConnectFourModel

    fun isColumnFull(column: Int): Boolean {
        // TODO
        // Returns true if the column is full, or false if it is not
        return false
    }

    fun isBoardFull(): Boolean {
        // TODO
        // Returns true if the board is full, or false if it is not
        return false
    }
}