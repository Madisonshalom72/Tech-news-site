package com.example.model

enum class MediaType {
    LIVE_STREAM,
    KEYNOTE_DEMO,
    PODCAST
}

data class MediaItem(
    val id: String,
    val title: String,
    val description: String,
    val type: MediaType,
    val category: String,
    val duration: String,
    val durationSeconds: Int,
    val date: String,
    val viewsOrListeners: String,
    val speakerOrHost: String,
    val speakerAvatarText: String,
    val imageUrl: String,
    val isLive: Boolean = false,
    val keyTakeaways: List<String> = emptyList(),
    val transcriptSample: String = "",
    val chapterTimestamps: List<Pair<String, String>> = emptyList() // "00:00" to "Introduction"
)

data class LiveChatMessage(
    val id: String,
    val user: String,
    val avatarColorHex: Long,
    val message: String,
    val timeOffsetSec: Int
)

data class MediaFilter(
    val selectedCategory: String = "All Media",
    val searchQuery: String = "",
    val mediaTypeFilter: MediaType? = null
)
