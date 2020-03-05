package siyateagan.example.translatorapp.ui.selectLanguage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject


class LanguageViewModelFactory<T : ViewModel> @Inject constructor(private val selectLanguageViewModel: SelectLanguageViewModel) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return selectLanguageViewModel as T
    }
}
