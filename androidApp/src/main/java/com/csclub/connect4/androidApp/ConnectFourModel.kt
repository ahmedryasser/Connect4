package com.csclub.connect4.androidApp

import java.lang.Integer.max
import kotlin.experimental.and
import kotlin.experimental.or

const val NUM_COLUMNS = 7
const val NUM_ROWS = 6
const val NUM_TO_WIN = 4
enum class Player { ONE, TWO, NONE }
enum class Victory { YES, NO, TIE}

/* Represents a Connect Four board with indices as follows:
    (0,NUM_ROWS - 1),   (1,NUM_ROWS - 1),   ...,    (NUM_COLUMNS - 1,NUM_ROWS - 1)
    ...
    (0,1),          (1,1),          ...,    (NUM_COLUMNS - 1,1)
    (0,0),          (1,0),          ...,    (NUM_COLUMNS - 1,0)
 */
interface ConnectFourModel {
    operator fun get(column: Int, row: Int): Player

    fun clone(): MutableConnectFourModel

    fun isColumnFull(column: Int): Boolean {
        for(x in 0 until NUM_ROWS){
            if(get(column, x) == Player.NONE){
                return false
            }
        }
        return true
    }

    fun isBoardFull(): Boolean {
        for(i in 0 until NUM_COLUMNS){
            if (!isColumnFull(i)) {
                return false
            }
        }
        return true
    }
}

// Implementation of ConnectFourModel that allows dropping discs in
class MutableConnectFourModel private constructor(private val p1Discs: ByteArray, private val p2Discs: ByteArray) : ConnectFourModel {
    constructor() : this(ByteArray(NUM_COLUMNS) { 0.toByte()}, ByteArray(NUM_COLUMNS) { 0.toByte() })

    fun dropDisc(player: Player, column: Int): Victory {
        for (i in (0 until NUM_ROWS)) {
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
        if(column !in 0 until NUM_COLUMNS || row !in 0 until NUM_ROWS) {
            return Player.NONE
        }
        val mask = (1 shl row).toByte()
        return when {
            p1Discs[column] and mask > 0.toByte() -> Player.ONE
            p2Discs[column] and mask > 0.toByte() -> Player.TWO
            else -> Player.NONE
        }
    }

    override fun clone(): MutableConnectFourModel = MutableConnectFourModel(p1Discs.copyOf(), p2Discs.copyOf())

    private fun isWinningMove(player: Player, column: Int, row: Int) : Victory {
        val numInARow = IntArray(4) { 0 }
        val maxInARow = IntArray(4) { 0 }
        for (i in -NUM_TO_WIN + 1 until NUM_TO_WIN){
            if (get(column + i, row + i) == player) {
                numInARow[0]++
                maxInARow[0] = max(numInARow[0], maxInARow[0])
            } else {
                numInARow[0] = 0
            }
            if (get(column - i, row + i) == player) {
                numInARow[1]++
                maxInARow[1] = max(numInARow[1], maxInARow[1])
            } else {
                numInARow[1] = 0
            }
            if (get(column, row + i) == player) {
                numInARow[2]++
                maxInARow[2] = max(numInARow[2], maxInARow[2])
            }
            else {
                numInARow[2] = 0
            }
            if (get(column + i, row) == player) {
                numInARow[3]++
                maxInARow[3] = max(numInARow[3], maxInARow[3])
            } else {
                numInARow[3] = 0
            }
        }
        return when {
            maxInARow.indexOfFirst { it >= NUM_TO_WIN } != -1 -> Victory.YES
            isBoardFull() -> Victory.TIE
            else -> Victory.NO
        }
    }
}