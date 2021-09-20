package com.example.mytrackerapp.model

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class LocationLogging(
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0
)
