package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Intent
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import javax.inject.Inject


class TextTranslationViewModel @Inject constructor() : ViewModel() {
    var currentLanguage = ObservableField("Select language")
    lateinit var currentLanguageCode: String

    fun setNewInputLanguage(data: Intent?) {
        val pair = data?.getSerializableExtra("languageWithCode") as Pair<*, *>
        currentLanguageCode = pair.first.toString()
        currentLanguage.set(pair.second.toString())
        currentLanguage.notifyChange()
    }
}