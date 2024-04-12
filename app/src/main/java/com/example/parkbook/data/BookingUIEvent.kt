package com.example.parkbook.data

sealed class BookingUIEvent {
    data class bookingEmployerChanged(val employer: String) : BookingUIEvent()
    data class bookingDateChanged(val bookingDate: String) : BookingUIEvent()
    data class bookingFromTimeChanged(val bookingFromTime: String) : BookingUIEvent()
    data class bookingToTimeChanged(val bookingToTime: String) : BookingUIEvent()

    data class bookingSpotChanged(val bookingSpot:String) : BookingUIEvent()

    object bookingButtonClicked : BookingUIEvent()
}