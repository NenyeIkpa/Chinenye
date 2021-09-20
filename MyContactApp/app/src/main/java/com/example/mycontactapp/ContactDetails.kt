package com.example.mycontactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ContactDetails : AppCompatActivity() {

    lateinit var name: TextView
    lateinit var phoneNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        name = findViewById(R.id.name_contactDetails)
        phoneNumber = findViewById(R.id.phone_contactDetails)

        name.text = intent.getStringExtra("name")
        phoneNumber.text = intent.getStringExtra("phoneNumber")



    }

    override fun onBackPressed() {
        val intent = Intent(this,ContactList::class.java)
        startActivity(intent)
    }
}