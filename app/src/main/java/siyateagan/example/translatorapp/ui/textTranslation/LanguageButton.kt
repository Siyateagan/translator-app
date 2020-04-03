package siyateagan.example.translatorapp.ui.textTranslation

import android.content.SharedPreferences
import androidx.databinding.ObservableField
import siyateagan.example.translatorapp.util.StringsHelper

class LanguageButton private constructor(
    private val sharedPref: SharedPreferences,
    private val languageString: String,
    private val languageCodeString: String
) {
    var languageCode: String? = null
    var language = ObservableField("Select language")

    companion object {
        fun createCurrentButton(sharedPref: SharedPreferences, stringsHelper: StringsHelper)
                : LanguageButton {
            val languageString = stringsHelper.getCurrentLanguage()
            val languageCodeString = stringsHelper.getCurrentLanguageCode()
            return LanguageButton(sharedPref, languageString, languageCodeString)
        }

        fun createTargetButton(sharedPref: SharedPreferences, stringsHelper: StringsHelper)
                : LanguageButton {
            val languageString = stringsHelper.getTargetLanguage()
            val languageCodeString = stringsHelper.getTargetLanguageCode()
            return LanguageButton(sharedPref, languageString, languageCodeString)
        }
    }

    fun setLanguage(codeWithLanguage: Pair<String, String>) {
        languageCode = codeWithLanguage.first
        language.set(codeWithLanguage.second)
    }

    fun checkPair(pair: Pair<String?, String?>) {
        if (pair.second.isNullOrBlank()) return
        language.set(pair.second)
        languageCode = pair.first!!
    }

    fun getStringsPair(): Pair<String?, String?> = Pair(languageCodeString, languageString)
    fun getStringsPrefPair(): Pair<String?, String?> =
        Pair(sharedPref.getString(languageCodeString, ""), sharedPref.getString(languageString, ""))
    fun getCodeAndLanguagePair(): Pair<String?, String?> = Pair(languageCode, language.get())
}