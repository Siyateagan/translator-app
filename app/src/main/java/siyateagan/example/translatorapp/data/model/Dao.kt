package siyateagan.example.translatorapp.data.model

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert
    fun insert(favoritesEntity: FavoritesEntity)

    @Update
    fun update(favoritesEntity: FavoritesEntity)

    @Delete
    fun delete(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM FavoritesEntity")
    fun getAll(): List<FavoritesEntity>
}