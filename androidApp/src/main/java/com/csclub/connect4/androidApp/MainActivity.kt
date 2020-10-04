package com.csclub.connect4.androidApp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
                putBoolean("p1IsAI", !binding.player1HumanCheckbox.isChecked)
                putBoolean("p2IsAI", !binding.player2HumanCheckbox.isChecked)
            })
        })
    }
}
