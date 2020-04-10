package siyateagan.example.translatorapp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import siyateagan.example.translatorapp.data.observer.LanguagesObserver

@Module
class LanguagesObserverModule {
    @Provides
    fun provideLanguagesObserver(recyclerAdapter: LanguagesAdapter, applicationContext: Context)
            : LanguagesObserver =
        LanguagesObserver(
            recyclerAdapter,
            applicationContext
        )
}