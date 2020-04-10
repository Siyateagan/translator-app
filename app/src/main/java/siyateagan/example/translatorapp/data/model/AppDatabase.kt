package siyateagan.example.translatorapp.data.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritesEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun translationDao(): Dao
}