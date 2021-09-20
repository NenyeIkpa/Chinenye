package com.example.mycontactapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(var contactList: List<Contact>, var contactItemListener: ItemClicked) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(contactView: View) : RecyclerView.ViewHolder(contactView),
        View.OnClickListener {
        var firstLetter = contactView.findViewById<TextView>(R.id.firstLetter_tv)
        var contactName = contactView.findViewById<TextView>(R.id.contactName_tv)
        init {
            contactView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                contactItemListener.clickItem(position)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_recycler, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val itemPosition = contactList[position]
        holder.apply {
            firstLetter.text = itemPosition.contactSymbol
            contactName.text = itemPosition.contactName
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}