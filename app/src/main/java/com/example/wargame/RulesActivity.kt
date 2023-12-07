package com.example.wargame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class RulesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)

        val button = findViewById<Button>(R.id.playButton)

        val test = findViewById<Button>(R.id.button)

        test.setOnClickListener{
            goToTest ()
        }

        button.setOnClickListener {
            Log.d("play", "again")
            Game()
        }


    }

    fun goToTest() {
        val intent = Intent(this, TestActivity::class.java)
        startActivity(intent)
    }

    fun Game() {
        Log.d("Play", "again")
        val intent = Intent(this, Game::class.java)
        startActivity(intent)
    }

}