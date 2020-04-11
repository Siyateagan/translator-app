package siyateagan.example.translatorapp.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import siyateagan.example.translatorapp.data.model.AppDatabase
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideTranslationDao(applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "favorites_database"
    ).build().translationDao()
}
