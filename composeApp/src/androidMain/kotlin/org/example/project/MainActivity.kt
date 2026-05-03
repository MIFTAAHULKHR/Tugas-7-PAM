package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.example.project.database.NoteDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val databaseDriverFactory = DatabaseDriverFactory(this)

        // Perbaikan: Kita tidak perlu lagi membuat NoteEntity.Adapter secara manual
        // karena kita sudah menangani konversi tipe data di NoteDataSource.kt
        val noteDatabase = NoteDatabase(
            driver = databaseDriverFactory.createDriver()
        )

        val noteDataSource = NoteDataSource(noteDatabase)
        val dataStoreFactory = DataStoreFactory(this)
        val settingsDataSource = SettingsDataSource(dataStoreFactory.create())

        setContent {
            App(noteDataSource, settingsDataSource)
        }
    }
}