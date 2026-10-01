package com.example.data

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.tasks.await

/**
 * DataRepository manages bookmark persistence using Firebase Firestore.
 * Supports real-time snapshot synchronization, adding, removing, and querying bookmarks.
 */
class DataRepository(
    private val firestore: FirebaseFirestore? = try {
        if (FirebaseApp.getApps(FirebaseApp.getInstance().applicationContext).isNotEmpty()) {
            FirebaseFirestore.getInstance()
        } else {
            null
        }
    } catch (e: Exception) {
        Log.w("DataRepository", "Firebase not yet initialized: ${e.message}")
        null
    }
) {
    private val collectionName = "bookmarks"

    /**
     * Observe real-time changes to saved bookmarks from Firestore.
     */
    fun getBookmarks(): Flow<List<SavedMediaEntity>> {
        val db = firestore ?: return emptyFlow()
        return callbackFlow {
            val listenerRegistration = db.collection(collectionName)
                .orderBy("savedAt", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.e("DataRepository", "Error observing bookmarks", error)
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val items = snapshot.documents.mapNotNull { doc ->
                            doc.toObject(SavedMediaEntity::class.java)?.copy(id = doc.id)
                        }
                        trySend(items)
                    }
                }
            awaitClose { listenerRegistration.remove() }
        }
    }

    /**
     * Add or update a bookmarked media item in Firestore.
     */
    suspend fun addBookmark(item: SavedMediaEntity): Result<Unit> {
        val db = firestore ?: return Result.failure(IllegalStateException("Firestore not available"))
        return try {
            db.collection(collectionName)
                .document(item.id)
                .set(item)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("DataRepository", "Failed to add bookmark: ${item.id}", e)
            Result.failure(e)
        }
    }

    /**
     * Remove a bookmark by its media ID from Firestore.
     */
    suspend fun removeBookmark(id: String): Result<Unit> {
        val db = firestore ?: return Result.failure(IllegalStateException("Firestore not available"))
        return try {
            db.collection(collectionName)
                .document(id)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("DataRepository", "Failed to remove bookmark: $id", e)
            Result.failure(e)
        }
    }

    /**
     * Check if a specific media item is bookmarked.
     */
    suspend fun isBookmarked(id: String): Boolean {
        val db = firestore ?: return false
        return try {
            val snapshot = db.collection(collectionName).document(id).get().await()
            snapshot.exists()
        } catch (e: Exception) {
            Log.e("DataRepository", "Failed to check bookmark status for $id", e)
            false
        }
    }
}
