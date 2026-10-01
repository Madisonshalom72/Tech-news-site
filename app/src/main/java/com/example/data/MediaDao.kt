package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {
    @Query("SELECT * FROM saved_media ORDER BY savedAt DESC")
    fun getAllSavedMedia(): Flow<List<SavedMediaEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM saved_media WHERE id = :id)")
    fun isMediaSaved(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMedia(item: SavedMediaEntity)

    @Query("DELETE FROM saved_media WHERE id = :id")
    suspend fun deleteSavedMediaById(id: String)

    @Query("SELECT * FROM playback_history ORDER BY lastUpdated DESC")
    fun getPlaybackHistory(): Flow<List<PlaybackHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun recordPlaybackProgress(history: PlaybackHistoryEntity)

    @Query("DELETE FROM playback_history WHERE mediaId = :mediaId")
    suspend fun deleteHistory(mediaId: String)
}
