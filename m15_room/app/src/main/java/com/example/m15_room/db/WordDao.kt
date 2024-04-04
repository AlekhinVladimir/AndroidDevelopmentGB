package com.example.m15_room.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM word_table ORDER BY count DESC LIMIT :stringCount")
    fun getMaxCountStringSet(stringCount: Int): Flow<List<Word>>

    @Query("SELECT count FROM word_table WHERE word = :word")
    suspend fun getCount(word: String): Int?

    @Insert
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun delete()

    @Update
    suspend fun update(word: Word)
}
