package com.example.parkbook.firebase

import android.app.Application
import com.google.firebase.FirebaseApp

class FirebaseInitApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}