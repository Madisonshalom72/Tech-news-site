package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleDataProvider
import com.example.model.MediaItem
import com.example.model.MediaType
import com.example.ui.components.CategoryFilterRow
import com.example.ui.components.FeaturedHeroCard
import com.example.ui.components.KeynoteDemoCard
import com.example.ui.components.PodcastEpisodeCard

@Composable
fun HomeScreen(
    selectedCategory: String,
    searchQuery: String,
    savedIds: Set<String>,
    activeMediaId: String?,
    isPlaying: Boolean,
    liveViewerCount: Int,
    onCategorySelected: (String) -> Unit,
    onJoinLiveStream: () -> Unit,
    onPlayMedia: (MediaItem) -> Unit,
    onToggleSave: (MediaItem) -> Unit,
    onOpenDetail: (MediaItem) -> Unit,
    onShareSection: (sectionTitle: String, category: String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    // Filter items based on category and search query
    val allItems = SampleDataProvider.allMediaItems

    val filteredItems = allItems.filter { item ->
        val matchesCategory = when (selectedCategory) {
            "All Media" -> true
            "Live Keynotes" -> item.isLive || item.type == MediaType.KEYNOTE_DEMO
            "Podcasts" -> item.type == MediaType.PODCAST
            else -> item.category.equals(selectedCategory, ignoreCase = true)
        }
        val matchesSearch = if (searchQuery.isBlank()) true else {
            item.title.contains(searchQuery, ignoreCase = true) ||
                item.description.contains(searchQuery, ignoreCase = true) ||
                item.speakerOrHost.contains(searchQuery, ignoreCase = true) ||
                item.category.contains(searchQuery, ignoreCase = true)
        }
        matchesCategory && matchesSearch
    }

    val featuredHero = SampleDataProvider.featuredLiveStream
    val showFeaturedHero = (selectedCategory == "All Media" || selectedCategory == "Live Keynotes" || selectedCategory == "Silicon & Quantum") &&
        (searchQuery.isBlank() || featuredHero.title.contains(searchQuery, ignoreCase = true))

    val keynoteDemos = filteredItems.filter { it.type == MediaType.KEYNOTE_DEMO }
    val podcasts = filteredItems.filter { it.type == MediaType.PODCAST }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_feed")
    ) {
        // Page Title & Header Section matching HTML prompt
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "BROADCAST & AUDIO HUB",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Tech Streams & Podcasts",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Category Filter Chips
        item {
            CategoryFilterRow(
                categories = SampleDataProvider.categories,
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // Featured Hero Banner / Live Stream
        if (showFeaturedHero) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    FeaturedHeroCard(
                        item = featuredHero,
                        liveViewerCount = liveViewerCount,
                        isSaved = savedIds.contains(featuredHero.id),
                        onJoinLiveStream = onJoinLiveStream,
                        onToggleSave = { onToggleSave(featuredHero) },
                        onCardClick = { onOpenDetail(featuredHero) }
                    )
                }
            }
        }

        // Empty Search Results State
        if (filteredItems.isEmpty() && !showFeaturedHero) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.SearchOff,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No media found",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Try adjusting your search query or selected category filter.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Featured Product Demos & Keynotes Grid Section
        if (keynoteDemos.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Featured Product Demos & Keynotes",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { onShareSection("Featured Product Demos & Keynotes", "Keynotes") },
                        modifier = Modifier.testTag("share_section_keynotes")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share keynotes section",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            items(keynoteDemos, key = { it.id }) { demo ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    KeynoteDemoCard(
                        item = demo,
                        isSaved = savedIds.contains(demo.id),
                        onCardClick = { onOpenDetail(demo) },
                        onPlayClick = { onPlayMedia(demo) },
                        onToggleSave = { onToggleSave(demo) }
                    )
                }
            }
        }

        // Popular Podcast Episodes Section
        if (podcasts.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Popular Podcast Episodes",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { onShareSection("Popular Podcast Episodes", "Podcasts") },
                        modifier = Modifier.testTag("share_section_podcasts")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share podcasts section",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            items(podcasts, key = { it.id }) { podcast ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    PodcastEpisodeCard(
                        item = podcast,
                        isSaved = savedIds.contains(podcast.id),
                        isPlaying = activeMediaId == podcast.id && isPlaying,
                        onCardClick = { onOpenDetail(podcast) },
                        onPlayClick = { onPlayMedia(podcast) },
                        onToggleSave = { onToggleSave(podcast) }
                    )
                }
            }
        }

        // Bottom space so mini player doesn't hide content
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
