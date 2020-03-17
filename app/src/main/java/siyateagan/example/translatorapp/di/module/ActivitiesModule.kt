package siyateagan.example.translatorapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import siyateagan.example.translatorapp.ui.favorites.FavoritesActivity
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguageActivity
import siyateagan.example.translatorapp.ui.textTranslation.TextTranslationActivity

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun contributeFavoritesActivityInjector(): FavoritesActivity

    @ContributesAndroidInjector
    abstract fun contributeSelectLanguageActivityInjector(): SelectLanguageActivity

    @ContributesAndroidInjector
    abstract fun contributeTextTranslationActivityInjector(): TextTranslationActivity
}
