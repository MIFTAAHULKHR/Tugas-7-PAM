package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.cash.sqldelight.adapter.primitive.BooleanColumnAdapter
import org.example.project.database.NoteDatabase
import org.example.project.database.NoteEntity

fun main() {
    val databaseDriverFactory = DatabaseDriverFactory()
    
    val noteDatabase = NoteDatabase(
        driver = databaseDriverFactory.createDriver(),
        NoteEntityAdapter = NoteEntity.Adapter(
            isFavoriteAdapter = BooleanColumnAdapter
        )
    )
    
    val noteDataSource = NoteDataSource(noteDatabase)
    val dataStoreFactory = DataStoreFactory()
    val settingsDataSource = SettingsDataSource(dataStoreFactory.create())

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KotlinProject",
        ) {
            App(noteDataSource, settingsDataSource)
        }
    }
}
