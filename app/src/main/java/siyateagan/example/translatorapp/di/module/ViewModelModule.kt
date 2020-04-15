package siyateagan.example.translatorapp.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import siyateagan.example.translatorapp.di.ViewModelKey
import siyateagan.example.translatorapp.ui.favorites.FavoritesVM
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguageVM
import siyateagan.example.translatorapp.ui.textTranslation.TextTranslationVM


@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FavoritesVM::class)
    internal abstract fun favoritesViewModel(viewModel: FavoritesVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectLanguageVM::class)
    internal abstract fun selectLanguageViewModel(viewModel: SelectLanguageVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TextTranslationVM::class)
    internal abstract fun textTranslationViewModel(viewModel: TextTranslationVM): ViewModel
}