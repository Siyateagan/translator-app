package siyateagan.example.translatorapp.data.model

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert
    fun insert(favoritesEntity: FavoritesEntity)

    @Delete
    fun delete(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM FavoritesEntity")
    fun getAll(): List<FavoritesEntity>

    @Query("SELECT * FROM FavoritesEntity WHERE current LIKE :current AND target LIKE :target")
    fun contains(current: String, target: String): FavoritesEntity
}