package com.example.parkingspotfinder.utils

fun String.wrap(maxLength: Int): String{
    if(this.length <= maxLength - 1) return this
    else {
        var shortenedText = this.substring(0, maxLength - 1)
        val temp = this.substring(maxLength - 1)
        for(i in temp.indices){
            if(temp[i] == ' ')
                break
            else shortenedText += temp[i]
        }

        return "$shortenedText..."
    }

}