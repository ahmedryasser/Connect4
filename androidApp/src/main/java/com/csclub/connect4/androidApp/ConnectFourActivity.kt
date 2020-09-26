package com.csclub.connect4.androidApp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.csclub.connect4.androidApp.databinding.ActivityConnectFourBinding

class ConnectFourActivity : AppCompatActivity(), ConnectFourView {
    private lateinit var model: ConnectFourViewModel
    private val connectFourPresenter
        get() = model.presenter
    private lateinit var binding: ActivityConnectFourBinding
    // Grid elements are [column][row]
    private lateinit var grid: Array<Array<ImageView>>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectFourBinding.inflate(layoutInflater)
        val view = binding.root
        initializeGrid()
        model = ViewModelProvider(this).get(ConnectFourViewModel::class.java)
        intent.extras?.run {
            connectFourPresenter.onGameStart(!getBoolean("p1"), !getBoolean("p2"))
        }
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        connectFourPresenter.subscribeToUpdates(this)
    }

    override fun onPause() {
        super.onPause()
        connectFourPresenter.unsubscribeToUpdates()
    }

    fun onClick(v: View) {
        // TODO
        // Finds the column of the ImageView that was clicked
        // Calls onGridClick() in the presenter
    }

    override fun updateUI() {
        // TODO
        // Updates each of the ImageViews based on the model in the presenter
        // Should set the image to ic_connect4red for player 1, ic_connect4blue for player 2,
        // and ic_connect4empty for no player (they're Drawables in the res folder)
        // Updates the current player TextView
        // Updates the winning player TextView (should be hidden if no winner)
        // Try to find documentation for how to do these View operations on the internet
    }

    private fun initializeGrid() {
        grid = arrayOf(
            arrayOf(binding.connectFourGridRow0Column0, binding.connectFourGridRow1Column0, binding.connectFourGridRow2Column0, binding.connectFourGridRow3Column0, binding.connectFourGridRow4Column0, binding.connectFourGridRow5Column0),
            arrayOf(binding.connectFourGridRow0Column1, binding.connectFourGridRow1Column1, binding.connectFourGridRow2Column1, binding.connectFourGridRow3Column1, binding.connectFourGridRow4Column1, binding.connectFourGridRow5Column1),
            arrayOf(binding.connectFourGridRow0Column2, binding.connectFourGridRow1Column2, binding.connectFourGridRow2Column2, binding.connectFourGridRow3Column2, binding.connectFourGridRow4Column2, binding.connectFourGridRow5Column2),
            arrayOf(binding.connectFourGridRow0Column3, binding.connectFourGridRow1Column3, binding.connectFourGridRow2Column3, binding.connectFourGridRow3Column3, binding.connectFourGridRow4Column3, binding.connectFourGridRow5Column3),
            arrayOf(binding.connectFourGridRow0Column4, binding.connectFourGridRow1Column4, binding.connectFourGridRow2Column4, binding.connectFourGridRow3Column4, binding.connectFourGridRow4Column4, binding.connectFourGridRow5Column4),
            arrayOf(binding.connectFourGridRow0Column5, binding.connectFourGridRow1Column5, binding.connectFourGridRow2Column5, binding.connectFourGridRow3Column5, binding.connectFourGridRow4Column5, binding.connectFourGridRow5Column5),
            arrayOf(binding.connectFourGridRow0Column6, binding.connectFourGridRow1Column6, binding.connectFourGridRow2Column6, binding.connectFourGridRow3Column6, binding.connectFourGridRow4Column6, binding.connectFourGridRow5Column6)
        )
    }
}