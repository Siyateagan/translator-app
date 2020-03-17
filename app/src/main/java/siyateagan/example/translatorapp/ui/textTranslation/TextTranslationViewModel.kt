package siyateagan.example.translatorapp.ui.textTranslation

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import javax.inject.Inject


class TextTranslationViewModel @Inject constructor(): ViewModel() {
    var currentLanguage = ObservableField("Select language")
}