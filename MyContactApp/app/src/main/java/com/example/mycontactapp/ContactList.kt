package com.example.mycontactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContactList : AppCompatActivity(), ItemClicked {
    private lateinit var database:DatabaseReference
    private lateinit var contactRV:RecyclerView
    private lateinit var  newList:MutableList<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)
        newList = mutableListOf()
        contactRV = findViewById(R.id.contactList_rv)
        getContactInformationFromFirebase()



        contactRV.layoutManager = LinearLayoutManager(this)


        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val intent = Intent(this, AddContact::class.java)
            startActivity(intent)
        }

    }
    private fun getContactInformationFromFirebase() {
        database = FirebaseDatabase.getInstance().getReference("Contacts")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    newList.clear()
                    for (i in snapshot.children) {
                        val fullName = i.child("contactName").getValue()
                        val fullSymbol = i.child("contactSymbol").getValue()
                        val fullNumber = i.child("phoneNumber").getValue()
                       newList.add(
                            Contact(
                                fullSymbol.toString(), fullName.toString(),
                                fullNumber.toString()
                            )
                        )
                    }

                    contactRV.adapter = ContactAdapter(newList, this@ContactList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun clickItem(position: Int) {
        val intent = Intent(this, ContactDetails::class.java)
        intent.putExtra("name", newList[position].contactName)
        intent.putExtra("phoneNumber", newList[position].phoneNumber)
        startActivity(intent)
    }
}