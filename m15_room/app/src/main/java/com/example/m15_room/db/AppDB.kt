package com.example.m15_room.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun wordDao(): WordDao
}
