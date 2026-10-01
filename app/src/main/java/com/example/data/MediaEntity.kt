package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_media")
data class SavedMediaEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val description: String = "",
    val type: String = "", // LIVE_STREAM, KEYNOTE_DEMO, PODCAST
    val category: String = "",
    val duration: String = "",
    val date: String = "",
    val viewsOrListeners: String = "",
    val speakerOrHost: String = "",
    val speakerAvatarText: String = "",
    val imageUrl: String = "",
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "playback_history")
data class PlaybackHistoryEntity(
    @PrimaryKey val mediaId: String = "",
    val title: String = "",
    val positionMs: Long = 0L,
    val durationMs: Long = 0L,
    val lastUpdated: Long = System.currentTimeMillis()
)
