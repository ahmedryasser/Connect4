package com.csclub.connect4.androidApp

class ConnectFourPresenter() {
    private var connectFourView: ConnectFourView? = null
    private var isP1Human = true
    private var isP2Human = true
    // Should never be NONE
    var currentPlayer = ConnectFourModel.Player.ONE
        private set
    // YES means that the player who went last won, TIE means a tie, and NO means the game is still going
    var victory = ConnectFourModel.Victory.NO
        private set
    private val _connectFourModel = ConnectFourModel()
    val connectFourModel: ConnectFourModelReadonly
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
        if (!isP1Human && !isP2Human) {
            onGridClick(connectFourAI.getNextMove(connectFourModel, currentPlayer))
        }
    }

    tailrec fun onGridClick(column: Int) {
        // TODO
        // Checks if the move is valid and the game is still going. If not, does nothing
        // Otherwise, drops a disc in the specified column, and updates whether the game is still going
        // Changes player to the next player
        // Updates the UI
        // If the now-current player is an AI, takes its turn unless the game is over
    }
}

interface ConnectFourView {
    fun updateUI()
}