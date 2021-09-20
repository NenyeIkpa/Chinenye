package com.example.mycontactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddContact : AppCompatActivity() {

    lateinit var nameView: TextInputEditText
    lateinit var phoneNumView: TextInputEditText
    lateinit var saveButton: Button
    lateinit var fireDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)


         nameView = findViewById(R.id.contactName_TILE)
        phoneNumView = findViewById(R.id.contactPhone_TILE)
        saveButton = findViewById(R.id.saveButton)
        fireDatabase = FirebaseDatabase.getInstance().getReference("Contacts")


        saveButton.setOnClickListener{
            val fullName = nameView.text?.trim().toString()
            val phoneNumber = phoneNumView.text?.trim().toString()
            val id =  ContactObject.getFirstLetterOfName(fullName)

            fireDatabase.child(fullName).setValue(
                Contact(id,fullName,phoneNumber))

            val intent = Intent(this, ContactDetails::class.java)
            intent.putExtra("name", fullName)
            intent.putExtra("phoneNumber", phoneNumber)
            Toast.makeText(this, "Yes", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }





    }
}