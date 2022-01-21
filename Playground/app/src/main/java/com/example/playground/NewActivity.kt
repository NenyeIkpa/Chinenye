package com.example.playground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        val newButton = findViewById<Button>(R.id.new_backButton)
        val startButton = findViewById<Button>(R.id.new_button1)
        val midButton = findViewById<Button>(R.id.new_button2)
        val endButton = findViewById<Button>(R.id.new_button3)

        newButton.setOnClickListener {
            finish()
        }

        val startFragment = StartFragment()
        val midFragment = MidFragment()
        val endFragment = EndFragment()
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.frameFragment, startFragment)
//            commit()
//        }
        startButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameFragment, startFragment)
                addToBackStack(null)
                commit()
            }
        }
        midButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameFragment, midFragment)
                addToBackStack(null)
                commit()
            }
            endButton.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frameFragment, endFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }
}