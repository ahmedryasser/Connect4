import SwiftUI
import shared

import UIKit

class ConnectFourVC: UIViewController, ConnectFourView {
    let presenter = ConnectFourPresenter()
    var grid: [[UIButton]]?

    override func viewDidLoad() {
        view.backgroundColor = .white
        
        setupView()

        // We need to do this after the UI is created to avoid nil object references
        self.presenter.subscribeToUpdates(view: self)
    }
    
    func setupView() {
        // TODO
        // Create the game grid
        // Create a label for the current player
        // Create a label for the winner
    }

    @objc func onGridClick(_ sender: UIButton) {
        // TODO
        // Finds the column of the ImageView that was clicked
        // Calls onGridClick() in the presenter
    }

    func updateUI() {
        // TODO
        // Updates each of the pieces of the grid based on the model in the presenter
        // Should set the image to ic_connect4red for player 1, ic_connect4blue for player 2,
        // and ic_connect4empty for no player
        // Updates the current player label
        // Updates the winner label (hidden if no winner)
    }
}