package siyateagan.example.translatorapp.di.component

import dagger.Component
import siyateagan.example.translatorapp.di.module.ViewModelModule
import siyateagan.example.translatorapp.di.module.ViewModelFactoryModule
import siyateagan.example.translatorapp.ui.favorites.FavoritesActivity
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguage

@Component(modules = [ViewModelModule::class, ViewModelFactoryModule::class])
interface AppComponent {
    // Classes that can be injected by this Component
    fun inject(activity: FavoritesActivity)
    fun inject(activity: SelectLanguage)
}
