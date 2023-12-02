package com.example.mpproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ParkBookmarkEntity::class], version = 1)
abstract class ParkDatabase : RoomDatabase() {
    abstract fun parkDao(): ParkDAO

    companion object {
        private var instance: ParkDatabase? = null
        @Synchronized
        fun getInstance(context: Context): ParkDatabase? {
            if (instance == null)
                synchronized(ParkDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ParkDatabase::class.java,
                        "park.db"
                    )
                        .build()
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}