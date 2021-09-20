package com.example.mycontactapp

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class Contact(
    var contactSymbol: String? = null,
    val contactName: String? = null,
    val phoneNumber: String? = null
) {

}