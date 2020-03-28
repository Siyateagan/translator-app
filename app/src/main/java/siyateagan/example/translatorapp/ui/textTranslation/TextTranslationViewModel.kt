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
    var translationLanguage: ObservableField<String> = ObservableField("Select language")
    private lateinit var currentLanguageCode: String
    private lateinit var translationLanguageCode: String

    fun setNewLanguage(requestCode: Int, data: Intent?) {
        val languageWithCode = data?.getSerializableExtra("languageWithCode") as Pair<*, *>?

        if (languageWithCode != null) {
            lateinit var codeWithLanguage: Pair<String, String>
            if (requestCode == 1) {
                codeWithLanguage = Pair(
                    applicationContext.getString(R.string.current_language_code),
                    applicationContext.getString(R.string.current_language)
                )

                currentLanguageCode = languageWithCode.first.toString()
                displayLanguage(currentLanguage, languageWithCode)
            } else {
                codeWithLanguage = Pair(
                    applicationContext.getString(R.string.translation_language_code),
                    applicationContext.getString(R.string.translation_language)
                )

                translationLanguageCode = languageWithCode.first.toString()
                displayLanguage(translationLanguage, languageWithCode)
            }

            with(sharedPref.edit()) {
                putString(codeWithLanguage.first, languageWithCode.first.toString())
                putString(codeWithLanguage.second, languageWithCode.second.toString())
                apply()
            }
        }
    }

    private fun displayLanguage(
        observableLanguage: ObservableField<String>,
        languageWithCode: Pair<*, *>?
    ) {
        observableLanguage.set(languageWithCode?.second.toString())
        observableLanguage.notifyChange()
    }

    /**
     * This method is called in TextTranslationActivity,
     * because its contents cannot be placed in the "init" block,
     * since applicationContext has not yet been initialized.
     */
    fun setPreviousLanguage() {
        val currentCodeAndLanguage = Pair(
            sharedPref.getString(applicationContext.getString(R.string.current_language_code), ""),
            sharedPref.getString(applicationContext.getString(R.string.current_language), "")
        )
        val translationCodeAndLanguage = Pair(
            sharedPref.getString(applicationContext.getString(R.string.translation_language_code), ""),
            sharedPref.getString(applicationContext.getString(R.string.translation_language), "")
        )

        if (!currentCodeAndLanguage.second.isNullOrBlank() && currentCodeAndLanguage.second != "null") {
            currentLanguage.set(currentCodeAndLanguage.second)
            currentLanguageCode = currentCodeAndLanguage.first!!
        }
        if (!translationCodeAndLanguage.second.isNullOrBlank() && translationCodeAndLanguage.second != "null") {
            translationLanguage.set(translationCodeAndLanguage.second)
            translationLanguageCode = translationCodeAndLanguage.first!!
        }
    }
}