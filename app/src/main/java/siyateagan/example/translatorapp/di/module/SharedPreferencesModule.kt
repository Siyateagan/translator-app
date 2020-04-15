package siyateagan.example.translatorapp.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import siyateagan.example.translatorapp.R
import javax.inject.Singleton

@Module
class SharedPreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(applicationContext: Context): SharedPreferences =
        applicationContext.getSharedPreferences(
            applicationContext.getString(R.string.pref_languages),
            Context.MODE_PRIVATE
        )
}