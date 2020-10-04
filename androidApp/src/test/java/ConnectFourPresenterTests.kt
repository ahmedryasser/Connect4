import com.csclub.connect4.androidApp.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ConnectFourPresenterTests {
    @Test
    fun onGridClickTest() {
        val presenter = ConnectFourPresenter()
        presenter.onGameStart(true, true)
        presenter.onGridClick(3)
        assertEquals(Player.TWO, presenter.currentPlayer)
        presenter.onGridClick(3)
        assertEquals(Player.ONE, presenter.currentPlayer)
        presenter.onGridClick(5)
        presenter.onGridClick(5)
        presenter.onGridClick(1)
        presenter.onGridClick(2)

        assertEquals(Player.ONE, presenter.connectFourModel[3,0])
        assertEquals(Player.TWO, presenter.connectFourModel[3,1])
        assertEquals(Player.ONE, presenter.connectFourModel[5,0])
        assertEquals(Player.TWO, presenter.connectFourModel[5,1])
        assertEquals(Player.ONE, presenter.connectFourModel[1,0])
        assertEquals(Player.TWO, presenter.connectFourModel[2,0])

        presenter.onGridClick(0)
        presenter.onGridClick(1)
        presenter.onGridClick(0)
        presenter.onGridClick(1)
        presenter.onGridClick(0)
        presenter.onGridClick(1)
        presenter.onGridClick(1)
        presenter.onGridClick(0)
        presenter.onGridClick(0)
        presenter.onGridClick(0)
        presenter.onGridClick(1)
        presenter.onGridClick(2)
        presenter.onGridClick(2)
        presenter.onGridClick(2)
        presenter.onGridClick(2)
        presenter.onGridClick(2)
        presenter.onGridClick(4)
        presenter.onGridClick(3)
        presenter.onGridClick(3)
        presenter.onGridClick(3)
        presenter.onGridClick(3)
        presenter.onGridClick(6)
        presenter.onGridClick(4)
        presenter.onGridClick(4)
        presenter.onGridClick(4)
        presenter.onGridClick(4)
        presenter.onGridClick(4)
        presenter.onGridClick(5)
        presenter.onGridClick(5)
        presenter.onGridClick(5)
        presenter.onGridClick(5)
        presenter.onGridClick(6)
        presenter.onGridClick(6)
        presenter.onGridClick(6)
        presenter.onGridClick(6)
        presenter.onGridClick(6)
        assertEquals(Victory.TIE, presenter.victory)

        val presenter2 = ConnectFourPresenter()
        presenter2.onGameStart(true, false)
        presenter2.onGridClick(3)
        assertEquals(Victory.NO, presenter2.victory)
        presenter2.onGridClick(3)
        assertEquals(Victory.NO, presenter2.victory)
        var numDiscsPlaced = 0
        for (i in 0 until NUM_COLUMNS) {
            for (j in 0 until NUM_ROWS) {
                if (presenter2.connectFourModel[i,j] != Player.NONE) {
                    numDiscsPlaced++
                }
            }
        }
        assertEquals(4, numDiscsPlaced)
        assertEquals(Player.ONE, presenter2.currentPlayer)

        val presenter3 = ConnectFourPresenter()
        presenter3.onGameStart(true, true)
        presenter3.onGridClick(3)
        presenter3.onGridClick(4)
        presenter3.onGridClick(3)
        presenter3.onGridClick(4)
        presenter3.onGridClick(3)
        presenter3.onGridClick(4)
        presenter3.onGridClick(3)
        assertEquals(Victory.YES, presenter3.victory)

        val presenter4 = ConnectFourPresenter()
        presenter4.onGameStart(false, false)
        assertTrue(presenter4.victory != Victory.NO)
    }
}