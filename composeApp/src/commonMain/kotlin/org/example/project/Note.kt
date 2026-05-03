package org.example.project

data class Note(
    val id: Long? = null,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false,
    val createdAt: Long = 0L
)
