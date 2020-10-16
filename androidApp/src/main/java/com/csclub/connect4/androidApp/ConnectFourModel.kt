package com.csclub.connect4.androidApp

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
=======
        val win = Array(8) { true }
        for (a in 0 until NUM_TO_WIN){
            if (player != get(column + a,row + a)){
                win[0] = false
            }
            if (player != get(column - a,row + a)){
                win[1] = false
            }
            if (player != get(column + a,row - a)){
                win[2] = false
            }
            if (player != get(column - a,row - a)){
                win[3] = false
            }
            if (player != get(column + a, row)){
                win[4] = false
            }
            if (player != get(column - a, row)){
                win[5] = false
            }
            if (player != get(column,row - a)){
                win[6] = false
            }
            if (player != get(column,row + a)){
                win[7] = false
            }
        }
        return when {
            win.contains(true) -> Victory.YES
            isBoardFull() -> Victory.TIE
            else -> Victory.NO
        }
    }
}