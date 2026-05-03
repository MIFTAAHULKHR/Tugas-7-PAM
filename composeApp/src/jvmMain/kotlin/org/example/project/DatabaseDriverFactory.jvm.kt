package org.example.project

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.example.project.database.NoteDatabase
import java.io.File

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val databaseStoragePath = File(System.getProperty("user.home"), "NoteDatabase.db")
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${databaseStoragePath.absolutePath}")
        if (!databaseStoragePath.exists()) {
            NoteDatabase.Schema.create(driver)
        }
        return driver
    }
}
