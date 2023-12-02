package com.example.mpproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ParkDAO {
    @Query("SELECT * FROM table_bookmark")
    fun getAll(): List<ParkBookmarkEntity>
    @Query("SELECT isSelected FROM table_bookmark WHERE name = :name")
    fun getBookmark(name: String) : Boolean
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBookmark(bookmarkEntity: ParkBookmarkEntity)
    @Query("DELETE FROM table_bookmark WHERE name = :name")
    fun deleteBookmark(name: String)
}