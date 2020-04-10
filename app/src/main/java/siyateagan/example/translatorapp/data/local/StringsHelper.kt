package siyateagan.example.translatorapp.data.local

import android.content.Context
import siyateagan.example.translatorapp.R
import javax.inject.Inject

class StringsHelper @Inject constructor(var applicationContext: Context) {
    fun getCurrentLanguage() = applicationContext.getString(R.string.current_language)
    fun getCurrentLanguageCode() = applicationContext.getString(R.string.current_language_code)
    fun getTargetLanguage() = applicationContext.getString(R.string.target_language)
    fun getTargetLanguageCode() = applicationContext.getString(R.string.target_language_code)

    fun currentButton() = applicationContext.getString(R.string.current_button)
    fun targetButton() = applicationContext.getString(R.string.target_button)
}