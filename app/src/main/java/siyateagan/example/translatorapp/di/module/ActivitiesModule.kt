package siyateagan.example.translatorapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import siyateagan.example.translatorapp.ui.favorites.FavoritesActivity
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguage
import siyateagan.example.translatorapp.ui.textTranslation.TextTranslationActivity

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun contributeFavoritesActivityInjector(): FavoritesActivity

    @ContributesAndroidInjector
    abstract fun contributeSelectLanguageActivityInjector(): SelectLanguage

    @ContributesAndroidInjector
    abstract fun contributeTextTranslationActivityInjector(): TextTranslationActivity
}
