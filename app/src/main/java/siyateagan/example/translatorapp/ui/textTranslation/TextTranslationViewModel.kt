package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import siyateagan.example.translatorapp.R
import javax.inject.Inject


class TextTranslationViewModel @Inject constructor() : ViewModel() {
    private val TAG = TextTranslationViewModel::class.java.simpleName

    @Inject
    lateinit var applicationContext: Context

    @Inject
    lateinit var sharedPref: SharedPreferences

    var currentLanguage: ObservableField<String> = ObservableField("Select language")
    private lateinit var currentLanguageCode: String

    fun setNewInputLanguage(data: Intent?) {
        val pair = data?.getSerializableExtra("languageWithCode") as Pair<*, *>?

        with(sharedPref.edit()) {
            putString(
                applicationContext.getString(R.string.current_language_code),
                pair?.first.toString()
            )
            putString(
                applicationContext.getString(R.string.current_language),
                pair?.second.toString()
            )
            apply()
        }

        if (pair != null) {
            currentLanguageCode = pair.first.toString()
            currentLanguage.set(pair.second.toString())
            currentLanguage.notifyChange()
        }
    }

    /**
     * This method is called in TextTranslationActivity,
     * because its contents cannot be placed in the "init" block,
     * since applicationContext has not yet been initialized.
     */
    fun setPreviousLanguage() {
        val savedLanguage =
            sharedPref.getString(applicationContext.getString(R.string.current_language), "")
        val savedLanguageCode =
            sharedPref.getString(applicationContext.getString(R.string.current_language_code), "")

        if (!savedLanguage.isNullOrBlank() && savedLanguage != "null") {
            currentLanguage.set(savedLanguage)
            currentLanguageCode = savedLanguageCode!!
        }
    }
}