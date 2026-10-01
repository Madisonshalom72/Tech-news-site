package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.MediaRepository
import com.example.data.SampleDataProvider
import com.example.data.SavedMediaEntity
import com.example.model.LiveChatMessage
import com.example.model.MediaItem
import com.example.model.MediaType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.UUID

data class MediaUiState(
    val selectedCategory: String = "All Media",
    val searchQuery: String = "",
    val activeNavTab: NavTab = NavTab.HOME,
    val activeMedia: MediaItem? = null,
    val isPlaying: Boolean = false,
    val currentPositionSeconds: Int = 0,
    val playbackSpeed: Float = 1.0f,
    val isFullScreenPlayerVisible: Boolean = false,
    val liveViewerCount: Int = 48240,
    val liveChatMessages: List<LiveChatMessage> = SampleDataProvider.liveChatMessages,
    val userReactionCount: Int = 0,
    val notificationAlerts: List<String> = SampleDataProvider.intelligenceFeedAlerts,
    val showNotificationsSheet: Boolean = false,
    val showProfileSheet: Boolean = false,
    val selectedDetailItem: MediaItem? = null
)

enum class NavTab {
    HOME,
    AI_CLOUD,
    VIDEO_PODCASTS,
    FOLLOWING
}

class MediaViewModel(private val repository: MediaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MediaUiState())
    val uiState: StateFlow<MediaUiState> = _uiState.asStateFlow()

    // Observe bookmarks from Room
    val savedMediaIds: StateFlow<Set<String>> = repository.savedMedia
        .combine(_uiState) { list, _ -> list.map { it.id }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    val savedMediaItems: StateFlow<List<SavedMediaEntity>> = repository.savedMedia
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private var playbackJob: Job? = null
    private var liveStreamJob: Job? = null

    init {
        startLiveStreamSimulation()
    }

    private fun startLiveStreamSimulation() {
        liveStreamJob?.cancel()
        liveStreamJob = viewModelScope.launch {
            var counter = 0
            while (isActive) {
                delay(3500)
                counter++
                _uiState.update { current ->
                    val jitter = ((-15..25).random())
                    val updatedViewers = (current.liveViewerCount + jitter).coerceAtLeast(1000)
                    current.copy(liveViewerCount = updatedViewers)
                }
            }
        }
    }

    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectNavTab(tab: NavTab) {
        _uiState.update { current ->
            val updatedCategory = when (tab) {
                NavTab.HOME -> "All Media"
                NavTab.AI_CLOUD -> "AI & Cloud"
                NavTab.VIDEO_PODCASTS -> "Podcasts"
                NavTab.FOLLOWING -> current.selectedCategory
            }
            current.copy(activeNavTab = tab, selectedCategory = updatedCategory)
        }
    }

    fun playMedia(item: MediaItem, openFullScreen: Boolean = false) {
        playbackJob?.cancel()
        _uiState.update {
            it.copy(
                activeMedia = item,
                isPlaying = true,
                currentPositionSeconds = if (item.isLive) 4200 else 0,
                isFullScreenPlayerVisible = openFullScreen
            )
        }
        startPlaybackTicker()
    }

    fun togglePlayPause() {
        val currentlyPlaying = _uiState.value.isPlaying
        if (currentlyPlaying) {
            playbackJob?.cancel()
            _uiState.update { it.copy(isPlaying = false) }
        } else {
            _uiState.update { it.copy(isPlaying = true) }
            startPlaybackTicker()
        }
    }

    private fun startPlaybackTicker() {
        playbackJob?.cancel()
        playbackJob = viewModelScope.launch {
            while (isActive) {
                val speed = _uiState.value.playbackSpeed
                val stepDelay = (1000L / speed).toLong().coerceAtLeast(200L)
                delay(stepDelay)
                _uiState.update { current ->
                    val media = current.activeMedia ?: return@update current
                    val nextSec = current.currentPositionSeconds + 1
                    if (!media.isLive && nextSec >= media.durationSeconds) {
                        current.copy(currentPositionSeconds = media.durationSeconds, isPlaying = false)
                    } else {
                        current.copy(currentPositionSeconds = nextSec)
                    }
                }
            }
        }
    }

    fun seekTo(seconds: Int) {
        val maxDuration = _uiState.value.activeMedia?.durationSeconds ?: 3600
        val target = seconds.coerceIn(0, maxDuration)
        _uiState.update { it.copy(currentPositionSeconds = target) }
    }

    fun skipForward15() {
        seekTo(_uiState.value.currentPositionSeconds + 15)
    }

    fun skipBackward15() {
        seekTo(_uiState.value.currentPositionSeconds - 15)
    }

    fun setPlaybackSpeed(speed: Float) {
        _uiState.update { it.copy(playbackSpeed = speed) }
    }

    fun openFullScreenPlayer() {
        _uiState.update { it.copy(isFullScreenPlayerVisible = true) }
    }

    fun closeFullScreenPlayer() {
        _uiState.update { it.copy(isFullScreenPlayerVisible = false) }
    }

    fun closePlayer() {
        playbackJob?.cancel()
        _uiState.update { it.copy(activeMedia = null, isPlaying = false, isFullScreenPlayerVisible = false) }
    }

    fun openItemDetail(item: MediaItem) {
        _uiState.update { it.copy(selectedDetailItem = item) }
    }

    fun closeItemDetail() {
        _uiState.update { it.copy(selectedDetailItem = null) }
    }

    fun toggleSaveMedia(item: MediaItem) {
        viewModelScope.launch {
            val isSaved = savedMediaIds.value.contains(item.id)
            if (isSaved) {
                repository.removeSavedMedia(item.id)
            } else {
                repository.saveMedia(
                    SavedMediaEntity(
                        id = item.id,
                        title = item.title,
                        description = item.description,
                        type = item.type.name,
                        category = item.category,
                        duration = item.duration,
                        date = item.date,
                        viewsOrListeners = item.viewsOrListeners,
                        speakerOrHost = item.speakerOrHost,
                        speakerAvatarText = item.speakerAvatarText,
                        imageUrl = item.imageUrl
                    )
                )
            }
        }
    }

    fun sendLiveChatMessage(messageText: String) {
        if (messageText.isBlank()) return
        val newMsg = LiveChatMessage(
            id = UUID.randomUUID().toString(),
            user = "You (Engineer)",
            avatarColorHex = 0xFF005BFF,
            message = messageText.trim(),
            timeOffsetSec = _uiState.value.currentPositionSeconds
        )
        _uiState.update {
            it.copy(liveChatMessages = it.liveChatMessages + newMsg)
        }
    }

    fun triggerReaction(emoji: String) {
        _uiState.update { it.copy(userReactionCount = it.userReactionCount + 1) }
    }

    fun toggleNotifications(show: Boolean) {
        _uiState.update { it.copy(showNotificationsSheet = show) }
    }

    fun toggleProfile(show: Boolean) {
        _uiState.update { it.copy(showProfileSheet = show) }
    }

    override fun onCleared() {
        super.onCleared()
        playbackJob?.cancel()
        liveStreamJob?.cancel()
    }
}

class MediaViewModelFactory(private val repository: MediaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MediaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
