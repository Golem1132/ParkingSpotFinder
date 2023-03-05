package com.example.parkingspotfinder.model

import android.graphics.Bitmap

data class UserInfo(
    var userId: String,
    var email: String,
    var fullName:String
) {
    fun toMap(): Map<String, String> =
        mapOf(
            "userId" to userId,
            "email" to email,
            "fullName" to fullName
        )
}
