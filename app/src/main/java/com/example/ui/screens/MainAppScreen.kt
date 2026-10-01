package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Podcasts
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.SampleDataProvider
import com.example.model.MediaItem
import com.example.ui.MediaViewModel
import com.example.ui.NavTab
import com.example.ui.components.FullScreenPlayerSheet
import com.example.ui.components.IntelligenceAlertsSheet
import com.example.ui.components.ItemDetailSheet
import com.example.ui.components.LiveStreamDialog
import com.example.ui.components.MiniPlayer
import com.example.ui.components.TechNewsHeader
import com.example.ui.components.UserProfileSheet
import com.example.ui.util.ShareHelper

@Composable
fun MainAppScreen(
    viewModel: MediaViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val savedIds by viewModel.savedMediaIds.collectAsStateWithLifecycle()
    val savedItems by viewModel.savedMediaItems.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var showLiveStreamDialog by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TechNewsHeader(
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                notificationCount = uiState.notificationAlerts.size,
                onNotificationClick = { viewModel.toggleNotifications(true) },
                onProfileClick = { viewModel.toggleProfile(true) },
                onShareClick = { ShareHelper.shareAppHub(context) }
            )
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Docked MiniPlayer above navigation bar if active
                AnimatedVisibility(
                    visible = uiState.activeMedia != null,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    uiState.activeMedia?.let { media ->
                        MiniPlayer(
                            activeMedia = media,
                            isPlaying = uiState.isPlaying,
                            currentPositionSeconds = uiState.currentPositionSeconds,
                            onTogglePlayPause = { viewModel.togglePlayPause() },
                            onExpand = {
                                if (media.isLive) {
                                    showLiveStreamDialog = true
                                } else {
                                    viewModel.openFullScreenPlayer()
                                }
                            },
                            onClose = { viewModel.closePlayer() }
                        )
                    }
                }

                // Standard M3 Navigation Bar with proper system insets
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    modifier = Modifier.testTag("bottom_navigation_bar")
                ) {
                    NavigationBarItem(
                        selected = uiState.activeNavTab == NavTab.HOME,
                        onClick = { viewModel.selectNavTab(NavTab.HOME) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.activeNavTab == NavTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = { Text("Home") },
                        modifier = Modifier.testTag("nav_tab_home")
                    )

                    NavigationBarItem(
                        selected = uiState.activeNavTab == NavTab.AI_CLOUD,
                        onClick = { viewModel.selectNavTab(NavTab.AI_CLOUD) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.activeNavTab == NavTab.AI_CLOUD) Icons.Filled.Cloud else Icons.Outlined.Cloud,
                                contentDescription = "AI & Cloud"
                            )
                        },
                        label = { Text("AI & Cloud") },
                        modifier = Modifier.testTag("nav_tab_ai_cloud")
                    )

                    NavigationBarItem(
                        selected = uiState.activeNavTab == NavTab.VIDEO_PODCASTS,
                        onClick = { viewModel.selectNavTab(NavTab.VIDEO_PODCASTS) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.activeNavTab == NavTab.VIDEO_PODCASTS) Icons.Filled.Podcasts else Icons.Outlined.Podcasts,
                                contentDescription = "Podcasts"
                            )
                        },
                        label = { Text("Podcasts") },
                        modifier = Modifier.testTag("nav_tab_podcasts")
                    )

                    NavigationBarItem(
                        selected = uiState.activeNavTab == NavTab.FOLLOWING,
                        onClick = { viewModel.selectNavTab(NavTab.FOLLOWING) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.activeNavTab == NavTab.FOLLOWING) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "Following"
                            )
                        },
                        label = { Text("Following") },
                        modifier = Modifier.testTag("nav_tab_following")
                    )
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState.activeNavTab) {
                NavTab.HOME, NavTab.AI_CLOUD, NavTab.VIDEO_PODCASTS -> {
                    HomeScreen(
                        selectedCategory = uiState.selectedCategory,
                        searchQuery = uiState.searchQuery,
                        savedIds = savedIds,
                        activeMediaId = uiState.activeMedia?.id,
                        isPlaying = uiState.isPlaying,
                        liveViewerCount = uiState.liveViewerCount,
                        onCategorySelected = { viewModel.selectCategory(it) },
                        onJoinLiveStream = {
                            val liveItem = SampleDataProvider.featuredLiveStream
                            viewModel.playMedia(liveItem, openFullScreen = false)
                            showLiveStreamDialog = true
                        },
                        onPlayMedia = { item ->
                            if (item.isLive) {
                                viewModel.playMedia(item, openFullScreen = false)
                                showLiveStreamDialog = true
                            } else {
                                viewModel.playMedia(item, openFullScreen = false)
                            }
                        },
                        onToggleSave = { viewModel.toggleSaveMedia(it) },
                        onOpenDetail = { viewModel.openItemDetail(it) },
                        onShareSection = { title, category ->
                            ShareHelper.shareSection(context, title, category)
                        }
                    )
                }
                NavTab.FOLLOWING -> {
                    FollowingScreen(
                        savedItems = savedItems,
                        onPlayMedia = { item ->
                            if (item.isLive) {
                                viewModel.playMedia(item, openFullScreen = false)
                                showLiveStreamDialog = true
                            } else {
                                viewModel.playMedia(item, openFullScreen = false)
                            }
                        },
                        onRemoveSaved = { id ->
                            val item = SampleDataProvider.allMediaItems.find { it.id == id }
                            if (item != null) viewModel.toggleSaveMedia(item)
                        },
                        onBrowseClick = { viewModel.selectNavTab(NavTab.HOME) },
                        onOpenDetail = { viewModel.openItemDetail(it) }
                    )
                }
            }
        }
    }

    // Live Stream Modal
    if (showLiveStreamDialog) {
        val streamItem = uiState.activeMedia ?: SampleDataProvider.featuredLiveStream
        LiveStreamDialog(
            streamItem = streamItem,
            viewerCount = uiState.liveViewerCount,
            chatMessages = uiState.liveChatMessages,
            reactionCount = uiState.userReactionCount,
            isSaved = savedIds.contains(streamItem.id),
            onSendMessage = { viewModel.sendLiveChatMessage(it) },
            onReaction = { viewModel.triggerReaction(it) },
            onToggleSave = {
                viewModel.toggleSaveMedia(streamItem)
            },
            onShareStream = {
                ShareHelper.shareMediaItem(context, streamItem)
            },
            onDismiss = { showLiveStreamDialog = false }
        )
    }

    // Full Screen Audio / Podcast Player Modal
    if (uiState.isFullScreenPlayerVisible && uiState.activeMedia != null) {
        FullScreenPlayerSheet(
            activeMedia = uiState.activeMedia!!,
            isPlaying = uiState.isPlaying,
            currentPositionSeconds = uiState.currentPositionSeconds,
            playbackSpeed = uiState.playbackSpeed,
            onTogglePlayPause = { viewModel.togglePlayPause() },
            onSeekTo = { viewModel.seekTo(it) },
            onSkipForward = { viewModel.skipForward15() },
            onSkipBackward = { viewModel.skipBackward15() },
            onSpeedChange = { viewModel.setPlaybackSpeed(it) },
            onDismiss = { viewModel.closeFullScreenPlayer() }
        )
    }

    // Item Detail Sheet
    uiState.selectedDetailItem?.let { detailItem ->
        ItemDetailSheet(
            item = detailItem,
            isSaved = savedIds.contains(detailItem.id),
            onPlayClick = {
                if (detailItem.isLive) {
                    viewModel.playMedia(detailItem, openFullScreen = false)
                    showLiveStreamDialog = true
                } else {
                    viewModel.playMedia(detailItem, openFullScreen = false)
                }
            },
            onToggleSave = { viewModel.toggleSaveMedia(detailItem) },
            onShareClick = { ShareHelper.shareMediaItem(context, detailItem) },
            onDismiss = { viewModel.closeItemDetail() }
        )
    }

    // Intelligence Feed Notifications Sheet
    if (uiState.showNotificationsSheet) {
        IntelligenceAlertsSheet(
            alerts = uiState.notificationAlerts,
            onDismiss = { viewModel.toggleNotifications(false) }
        )
    }

    // User Profile Sheet
    if (uiState.showProfileSheet) {
        UserProfileSheet(
            onDismiss = { viewModel.toggleProfile(false) }
        )
    }
}
