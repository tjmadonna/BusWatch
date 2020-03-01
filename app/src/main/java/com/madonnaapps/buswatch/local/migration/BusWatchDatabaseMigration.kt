package com.madonnaapps.buswatch.local.migration

import android.content.ContentValues
import androidx.room.OnConflictStrategy
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object BusWatchDatabaseMigration {

    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS favorite_stop (" +
                        "code INTEGER PRIMARY KEY NOT NULL," +
                        "title TEXT NOT NULL," +
                        "user_title TEXT NOT NULL)"
            )
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Remove stops table so we can just create a new one
            db.execSQL("DROP TABLE IF EXISTS stops;")

            // Create new stops table
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS stops (" +
                        "id TEXT NOT NULL, " +
                        "code TEXT NOT NULL, " +
                        "title TEXT NOT NULL, " +
                        "latitude REAL NOT NULL, " +
                        "longitude REAL NOT NULL, " +
                        "routes TEXT NOT NULL, " +
                        "PRIMARY KEY(id))"
            )

            // Create favorite stops table
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS favorite_stops (" +
                        "stop_id TEXT NOT NULL, " +
                        "user_title TEXT, " +
                        "PRIMARY KEY(stop_id), " +
                        "FOREIGN KEY(stop_id) REFERENCES stops(id) " +
                        "ON UPDATE CASCADE ON DELETE CASCADE)"
            )

            // Create stop version table
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS stop_version (" +
                        "id INTEGER NOT NULL, " +
                        "version INTEGER NOT NULL, " +
                        "PRIMARY KEY(id))"
            )

            // Create last location table
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS last_location (" +
                        "id INTEGER NOT NULL, " +
                        "latitude REAL NOT NULL, " +
                        "longitude REAL NOT NULL, " +
                        "zoom REAL NOT NULL, " +
                        "PRIMARY KEY(id))"
            )
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            val cursor = db.query("SELECT id FROM stops " +
                    "INNER JOIN favorite_stop favorite_stop.code = stops.code")

            if (cursor.count > 0) {
                db.beginTransaction()
                try {
                    while (cursor.moveToNext()) {
                        val values = ContentValues(1)
                        values.put("stop_id", cursor.getString(0))
                        db.insert("favorite_stops", OnConflictStrategy.REPLACE, values)
                    }
                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                    cursor.close()
                }
            }

            db.execSQL("DROP TABLE IF EXISTS favorite_stop;")
        }
    }
}