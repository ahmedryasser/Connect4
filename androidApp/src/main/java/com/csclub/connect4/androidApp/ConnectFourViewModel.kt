package com.csclub.connect4.androidApp

import androidx.lifecycle.ViewModel

class ConnectFourViewModel : ViewModel() {
    val presenter = ConnectFourPresenter()
}