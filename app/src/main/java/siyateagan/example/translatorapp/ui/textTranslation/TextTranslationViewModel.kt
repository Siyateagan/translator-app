package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.network.YandexService
import siyateagan.example.translatorapp.util.ParcelablePair
import siyateagan.example.translatorapp.util.StringsHelper
import javax.inject.Inject

class TextTranslationViewModel @Inject constructor(
    private val sharedPref: SharedPreferences,
    private val stringsHelper: StringsHelper,
    private val yandexService: YandexService
) : ViewModel() {
    private val TAG = TextTranslationViewModel::class.java.simpleName

    var currentLanguage = ObservableField("Select language")
    var targetLanguage = ObservableField("Select language")
    private var currentLanguageCode: String? = null
    private var targetLanguageCode: String? = null

    var translatedText = ObservableField("")
    var textToTranslate: String? = null

    fun setNewLanguage(requestCode: Int, data: Intent?) {
        val codeWithLanguage: Pair<String, String> =
            data?.getParcelableExtra<ParcelablePair<String, String>>("languageWithCode")?.pair
                ?: return

        val codeWithLanguageStrings: Pair<String, String> = getLanguagesStrings(requestCode)
        setSharedPrefData(codeWithLanguageStrings, codeWithLanguage)

        if (requestCode == 1) {
            setLanguage(currentLanguage, codeWithLanguage.second)
            currentLanguageCode = codeWithLanguage.first
        } else {
            setLanguage(targetLanguage, codeWithLanguage.second)
            targetLanguageCode = codeWithLanguage.first
        }
    }

    private fun getLanguagesStrings(
        requestCode: Int
    ): Pair<String, String> {
        return if (requestCode == 1)
            Pair(stringsHelper.getCurrentLanguageCode(), stringsHelper.getCurrentLanguage())
        else
            Pair(stringsHelper.getTargetLanguageCode(), stringsHelper.getTargetLanguage())
    }

    private fun setSharedPrefData(
        codeWithLanguageStrings: Pair<String, String>,
        codeWithLanguage: Pair<String, String>
    ) {
        with(sharedPref.edit()) {
            putString(codeWithLanguageStrings.first, codeWithLanguage.first)
            putString(codeWithLanguageStrings.second, codeWithLanguage.second)
            apply()
        }
    }

    private fun setLanguage(
        observableLanguage: ObservableField<String>,
        language: String
    ) {
        observableLanguage.set(language)
        observableLanguage.notifyChange()
    }

    private enum class LanguagesDirection {
        CURRENT, TARGET
    }

    /**
     * This method is called in TextTranslationActivity,
     * because its contents cannot be placed in the "init" block,
     * since applicationContext has not yet been initialized.
     */
    fun setPreviousLanguages() {
        val currentCodeAndLanguage = Pair(
            sharedPref.getString(stringsHelper.getCurrentLanguageCode(), ""),
            sharedPref.getString(stringsHelper.getCurrentLanguage(), "")
        )
        checkPair(currentCodeAndLanguage, LanguagesDirection.CURRENT)

        val translationCodeAndLanguage = Pair(
            sharedPref.getString(stringsHelper.getTargetLanguageCode(), ""),
            sharedPref.getString(stringsHelper.getTargetLanguage(), "")
        )
        checkPair(translationCodeAndLanguage, LanguagesDirection.TARGET)
    }

    private fun checkPair(pair: Pair<String?, String?>, directionName: LanguagesDirection) {
        if (pair.second.isNullOrBlank()) return
        when (directionName) {
            LanguagesDirection.CURRENT -> {
                currentLanguage.set(pair.second)
                currentLanguageCode = pair.first!!
            }
            LanguagesDirection.TARGET -> {
                targetLanguage.set(pair.second)
                targetLanguageCode = pair.first!!
            }
        }
    }

    fun swapLanguages() {
        currentLanguage = targetLanguage.also { targetLanguage = currentLanguage }
        currentLanguageCode = targetLanguageCode.also { targetLanguageCode = currentLanguageCode }
        currentLanguage.notifyChange()
        targetLanguage.notifyChange()
    }

    fun translateText(){
        if (textToTranslate.isNullOrBlank()) {
            translatedText.set("")
            return
        }
        val translateDirection = "$currentLanguageCode-$targetLanguageCode"

        Log.e(TAG, "$textToTranslate $translateDirection")
        val disposable = yandexService.translate(textToTranslate!!, translateDirection)
            .subscribeOn(Schedulers.io())
            .observeOn(mainThread())
            .subscribe({ result -> translatedText.set(result.text[0])},
                {error -> Log.e(TAG, "ERROR: ${error.message}")})
    }
}