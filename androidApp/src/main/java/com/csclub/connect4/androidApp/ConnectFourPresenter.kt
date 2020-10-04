package com.csclub.connect4.androidApp

class ConnectFourPresenter {
    private var connectFourView: ConnectFourView? = null
    private var isP1Human = true
    private var isP2Human = true
    // Should never be NONE
    var currentPlayer = Player.ONE
        private set
    // YES means that the player who went last won, TIE means a tie, and NO means the game is still going
    var victory = Victory.NO
        private set
    private val _connectFourModel = MutableConnectFourModel()
    val connectFourModel: ConnectFourModel
        get() = _connectFourModel
    private val connectFourAI: ConnectFourAI = DummyAI()

    fun subscribeToUpdates(view: ConnectFourView) {
        connectFourView = view
        view.updateUI()
    }

    fun unsubscribeToUpdates() {
        connectFourView = null
    }

    fun onGameStart(isP1Human: Boolean, isP2Human: Boolean) {
        this.isP1Human = isP1Human
        this.isP2Human = isP2Human
        if (!isP1Human) {
            onGridClick(connectFourAI.getNextMove(connectFourModel, currentPlayer))
        }
    }

    tailrec fun onGridClick(column: Int) {
        // TODO
        // Checks if the move is valid and the game is still going. If not, does nothing
        // Otherwise, drops a disc in the specified column, and updates victory
        // Changes player to the next player
        // Updates the UI
        // If the now-current player is an AI and the game is still going, takes its turn
    }
}

interface ConnectFourView {
    fun updateUI()
}