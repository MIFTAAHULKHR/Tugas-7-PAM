package org.example.project

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.database.NoteDatabase

class NoteDataSource(db: NoteDatabase) {
    private val queries = db.noteDatabaseQueries

    fun getAllNotes(): Flow<List<Note>> {
        return queries.getAllNotes()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map { it.toNote() }
            }
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        return queries.searchNotes(query)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map { it.toNote() }
            }
    }

    suspend fun getNoteById(id: Long): Note? {
        return queries.getNoteById(id).executeAsOneOrNull()?.toNote()
    }

    suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            // Perbaikan: Konversi Boolean ke Long (1L jika true, 0L jika false)
            isFavorite = if (note.isFavorite) 1L else 0L,
            createdAt = note.createdAt
        )
    }

    suspend fun deleteNote(id: Long) {
        queries.deleteNote(id)
    }

    suspend fun updateNote(id: Long, title: String, content: String) {
        queries.updateNote(title, content, id)
    }

    suspend fun toggleFavorite(id: Long, isFavorite: Boolean) {
        // Perbaikan: Konversi Boolean ke Long untuk query update
        queries.updateFavorite(if (isFavorite) 1L else 0L, id)
    }
}

fun org.example.project.database.NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        // Perbaikan: Konversi Long dari database kembali ke Boolean untuk model Note
        isFavorite = isFavorite == 1L,
        createdAt = createdAt
    )
}