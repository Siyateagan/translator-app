package siyateagan.example.translatorapp.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import siyateagan.example.translatorapp.di.ViewModelKey
import siyateagan.example.translatorapp.ui.favorites.FavoritesViewModel
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguageViewModel
import siyateagan.example.translatorapp.ui.textTranslation.TextTranslationViewModel


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    internal abstract fun favoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectLanguageViewModel::class)
    internal abstract fun selectLanguageViewModel(viewModel: SelectLanguageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TextTranslationViewModel::class)
    internal abstract fun textTranslationViewModel(viewModel: TextTranslationViewModel): ViewModel
}