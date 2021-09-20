package com.example.mycontactapp

object ContactObject {

    var contactList: MutableList<Contact> = mutableListOf(
        Contact("A", "Johnson Oyesina", "08148362111")
    )

    fun getFirstLetterOfName(name:String):String{
        return name[0].toUpperCase().toString()
    }
}