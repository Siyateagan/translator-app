package siyateagan.example.translatorapp.di

import dagger.Component
import siyateagan.example.translatorapp.ui.favorites.FavoritesActivity
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguage

@Component
interface AppComponent {
    // Classes that can be injected by this Component
    fun inject(activity: FavoritesActivity)
    fun inject(activity: SelectLanguage)
}
