package com.example.ui.util

import android.content.Context
import android.content.Intent
import com.example.model.MediaItem

object ShareHelper {

    fun shareAppHub(context: Context) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Explore TechNews - Broadcast & Audio Hub! Catch live keynotes, hardware demos, and premier technology podcasts: https://technews.app/hub"
            )
            putExtra(Intent.EXTRA_TITLE, "Share TechNews Hub")
            type = "text/plain"
        }
        val chooser = Intent.createChooser(sendIntent, "Share TechNews Hub")
        context.startActivity(chooser)
    }

    fun shareSection(context: Context, sectionTitle: String, category: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out the '$sectionTitle' ($category) on TechNews: https://technews.app/sections/${category.lowercase().replace(" ", "-")}"
            )
            putExtra(Intent.EXTRA_TITLE, "Share $sectionTitle")
            type = "text/plain"
        }
        val chooser = Intent.createChooser(sendIntent, "Share $sectionTitle")
        context.startActivity(chooser)
    }

    fun shareMediaItem(context: Context, item: MediaItem) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out '${item.title}' by ${item.speakerOrHost} on TechNews!\n\n${item.description}\n\nWatch/Listen: https://technews.app/media/${item.id}"
            )
            putExtra(Intent.EXTRA_TITLE, item.title)
            type = "text/plain"
        }
        val chooser = Intent.createChooser(sendIntent, "Share ${item.title}")
        context.startActivity(chooser)
    }
}
