package com.example.mycontactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         *  Delay movement from the splash screen activity to the sign up activity
         */
        Handler().postDelayed(
            {
                val i = Intent(this, ContactList::class.java)
                startActivity(i)
            }, 3000
        )
    }
}