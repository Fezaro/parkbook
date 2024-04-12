package com.example.parkbook.data

data class BookingData(
    val uid: String,
    val employer: String,
    val bookingDate: String,
    val bookingFromTime: String,
    val bookingToTime: String,
    val bookingSpot: String
){
    constructor(): this("","","","","","")
}