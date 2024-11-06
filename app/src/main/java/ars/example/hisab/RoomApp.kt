package ars.example.hisab

import android.app.Application
import ars.example.hisab.Model.database

class RoomApp:Application() {
    val db by lazy {
        database.getDataBaseInstance(this)
    }
}