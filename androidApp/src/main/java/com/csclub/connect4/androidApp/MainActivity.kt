package com.csclub.connect4.androidApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.csclub.connect4.androidApp.databinding.ActivityConnectFourBinding
import com.csclub.connect4.androidApp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun onClick(v: View) {
        startActivity(Intent(this, ConnectFourActivity::class.java).apply {
            putExtras(Bundle().apply {
                // Applies the "not" to make being human the default value if the bundle is not found for some reason
                putBoolean("p1", !binding.player1AiCheckbox.isChecked)
                putBoolean("p2", !binding.player2AiCheckbox.isChecked)
            })
        })
    }
}
