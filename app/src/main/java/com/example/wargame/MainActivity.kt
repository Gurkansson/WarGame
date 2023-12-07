package com.example.wargame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.startButton)


        button.setOnClickListener {
            start()
        }


    }

    fun start() {
        val intent = Intent(this, RulesActivity::class.java)
        startActivity(intent)
    }

//Git test 

}