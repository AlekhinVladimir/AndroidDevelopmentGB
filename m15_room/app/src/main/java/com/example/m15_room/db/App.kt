package com.example.m15_room.db

import android.app.Application
import androidx.room.Room

class App : Application() {
    lateinit var db: AppDB
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDB::class.java,
            "db"
        ).build()
    }
}
