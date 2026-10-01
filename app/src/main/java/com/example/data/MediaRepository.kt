package com.example.data

import kotlinx.coroutines.flow.Flow

class MediaRepository(private val mediaDao: MediaDao) {
    val savedMedia: Flow<List<SavedMediaEntity>> = mediaDao.getAllSavedMedia()
    val playbackHistory: Flow<List<PlaybackHistoryEntity>> = mediaDao.getPlaybackHistory()

    fun isMediaSaved(id: String): Flow<Boolean> = mediaDao.isMediaSaved(id)

    suspend fun saveMedia(item: SavedMediaEntity) = mediaDao.saveMedia(item)

    suspend fun removeSavedMedia(id: String) = mediaDao.deleteSavedMediaById(id)

    suspend fun updatePlayback(mediaId: String, title: String, positionMs: Long, durationMs: Long) {
        mediaDao.recordPlaybackProgress(
            PlaybackHistoryEntity(
                mediaId = mediaId,
                title = title,
                positionMs = positionMs,
                durationMs = durationMs,
                lastUpdated = System.currentTimeMillis()
            )
        )
    }

    suspend fun removeHistory(mediaId: String) = mediaDao.deleteHistory(mediaId)
}
