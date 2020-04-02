package siyateagan.example.translatorapp.ui.textTranslation

import android.content.SharedPreferences
import androidx.databinding.ObservableField
import siyateagan.example.translatorapp.util.StringsHelper

class LanguageButton(
    buttonType: String,
    sharedPref: SharedPreferences,
    stringsHelper: StringsHelper
) {
    var languageString: String?
    var languageCodeString: String?

    var languageCode: String? = null
    var language = ObservableField("Select language")

    init {
        if (buttonType == stringsHelper.getCurrentLanguage()){
            languageString = sharedPref.getString(stringsHelper.getCurrentLanguage(), "")
            languageCodeString = sharedPref.getString(stringsHelper.getCurrentLanguageCode(), "")
        } else {
            languageString = sharedPref.getString(stringsHelper.getTargetLanguage(), "")
            languageCodeString = sharedPref.getString(stringsHelper.getTargetLanguageCode(), "")
        }
    }

    fun setLanguage(codeWithLanguage: Pair<String, String>) {
        languageCode = codeWithLanguage.first
        language.set(codeWithLanguage.second)
    }

    fun checkPair(pair: Pair<String?, String?>) {
        if (language.get().isNullOrBlank()) return
        language.set(pair.second)
        languageCode = pair.first!!
    }

    fun getStringsPair(): Pair<String?, String?> = Pair(languageCodeString, languageString)
}