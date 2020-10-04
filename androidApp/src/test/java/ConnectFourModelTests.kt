import com.csclub.connect4.androidApp.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ConnectFourModelTests {
    @Test
    fun mutableConnectFourModelTest() {
        val model = MutableConnectFourModel()
        model.dropDisc(Player.ONE, 3)
        model.dropDisc(Player.TWO, 3)
        model.dropDisc(Player.ONE, 3)
        model.dropDisc(Player.TWO, 3)
        model.dropDisc(Player.ONE, 3)
        model.dropDisc(Player.TWO, 5)
        model.dropDisc(Player.ONE, 3)

        assertEquals(Player.ONE, model[3,0])
        assertEquals(Player.TWO, model[3,1])
        assertEquals(Player.ONE, model[3,2])
        assertEquals(Player.TWO, model[3,3])
        assertEquals(Player.ONE, model[3,4])
        assertEquals(Player.TWO, model[5,0])
        assertEquals(Player.ONE, model[3,5])
        assertEquals(Player.NONE, model[0,0])
        assertEquals(Player.NONE, model[1,0])
        assertEquals(Player.NONE, model[2,0])
        assertEquals(Player.NONE, model[4,0])
        assertEquals(Player.NONE, model[6,0])
        assertEquals(Player.NONE, model[0,5])
        assertEquals(Player.NONE, model[1,5])
        assertEquals(Player.NONE, model[2,5])
        assertEquals(Player.NONE, model[4,5])
        assertEquals(Player.NONE, model[6,5])

        val model2 = MutableConnectFourModel()
        val nonwinningMove1 = model2.dropDisc(Player.ONE, 3)
        val nonwinningMove2 = model2.dropDisc(Player.ONE, 3)
        val nonwinningMove3 = model2.dropDisc(Player.ONE, 3)
        val winningMove = model2.dropDisc(Player.ONE, 3)
        assertEquals(Victory.NO, nonwinningMove1)
        assertEquals(Victory.NO, nonwinningMove2)
        assertEquals(Victory.NO, nonwinningMove3)
        assertEquals(Victory.YES, winningMove)

        val model3 = MutableConnectFourModel()
        for (i in 0 until NUM_COLUMNS - 1) {
            if (i % 2 == 0) {
                model3.dropDisc(Player.ONE, i)
                model3.dropDisc(Player.ONE, i)
                model3.dropDisc(Player.ONE, i)
                model3.dropDisc(Player.TWO, i)
                model3.dropDisc(Player.TWO, i)
                model3.dropDisc(Player.TWO, i)
            } else {
                model3.dropDisc(Player.TWO, i)
                model3.dropDisc(Player.TWO, i)
                model3.dropDisc(Player.TWO, i)
                model3.dropDisc(Player.ONE, i)
                model3.dropDisc(Player.ONE, i)
                model3.dropDisc(Player.ONE, i)
            }
        }
        model3.dropDisc(Player.ONE, NUM_COLUMNS - 1)
        model3.dropDisc(Player.TWO, NUM_COLUMNS - 1)
        model3.dropDisc(Player.ONE, NUM_COLUMNS - 1)
        model3.dropDisc(Player.TWO, NUM_COLUMNS - 1)
        val nonwinningMove4 = model3.dropDisc(Player.ONE, NUM_COLUMNS - 1)
        val tyingMove = model3.dropDisc(Player.TWO, NUM_COLUMNS - 1)

        assertEquals(Victory.NO, nonwinningMove4)
        assertEquals(Victory.TIE, tyingMove)
    }

    @Test
    fun connectFourModelTest() {
        val model = MutableConnectFourModel()
        assertTrue(!model.isBoardFull())
        assertTrue(!model.isColumnFull(0))
        assertTrue(!model.isColumnFull(1))
        assertTrue(!model.isColumnFull(2))
        assertTrue(!model.isColumnFull(3))
        assertTrue(!model.isColumnFull(4))
        assertTrue(!model.isColumnFull(5))
        assertTrue(!model.isColumnFull(6))
        for (i in 0 until 3) {
            for (j in 0 until NUM_ROWS) {
                model.dropDisc(Player.ONE, i)
            }
        }

        assertTrue(!model.isBoardFull())
        assertTrue(model.isColumnFull(0))
        assertTrue(model.isColumnFull(1))
        assertTrue(model.isColumnFull(2))
        assertTrue(!model.isColumnFull(3))
        assertTrue(!model.isColumnFull(4))
        assertTrue(!model.isColumnFull(5))
        assertTrue(!model.isColumnFull(6))

        for (i in 3 until NUM_COLUMNS) {
            for (j in 0 until NUM_ROWS) {
                model.dropDisc(Player.ONE, i)
            }
        }

        assertTrue(model.isBoardFull())
        assertTrue(model.isColumnFull(0))
        assertTrue(model.isColumnFull(1))
        assertTrue(model.isColumnFull(2))
        assertTrue(model.isColumnFull(3))
        assertTrue(model.isColumnFull(4))
        assertTrue(model.isColumnFull(5))
        assertTrue(model.isColumnFull(6))
    }
}