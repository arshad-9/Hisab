package ars.example.hisab

import android.app.Application

class RoomApp:Application() {
    val db by lazy {
        database.getDataBaseInstance(this)
    }
}