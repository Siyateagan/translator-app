package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.network.YandexService
import siyateagan.example.translatorapp.network.observer.TranslateObserver
import siyateagan.example.translatorapp.util.ParcelablePair
import siyateagan.example.translatorapp.util.StringsHelper
import javax.inject.Inject

class TextTranslationViewModel @Inject constructor(
    private val sharedPref: SharedPreferences,
    stringsHelper: StringsHelper,
    private val yandexService: YandexService
) : ViewModel() {
    private val TAG = TextTranslationViewModel::class.java.simpleName

    var currentButton = LanguageButton.createCurrentButton(sharedPref, stringsHelper)
    var targetButton = LanguageButton.createTargetButton(sharedPref, stringsHelper)

    var textToTranslate: String? = null

    @Inject
    lateinit var translateObserver: TranslateObserver

    fun setNewLanguage(requestCode: Int, data: Intent?) {
        val codeWithLanguage =
            data?.getParcelableExtra<ParcelablePair<String, String>>("languageWithCode")?.toPair()
                ?: return

        val buttonForWork = if (requestCode == 1) currentButton else targetButton
        buttonForWork.setLanguage(codeWithLanguage)

        val codeWithLanguageStrings: Pair<String?, String?> = buttonForWork.getStringsPair()
        saveLanguage(codeWithLanguageStrings, codeWithLanguage)
    }

    private fun saveLanguage(
        codeWithLanguageStrings: Pair<String?, String?>,
        codeWithLanguage: Pair<String?, String?>
    ) {
        with(sharedPref.edit()) {
            putString(codeWithLanguageStrings.first, codeWithLanguage.first)
            putString(codeWithLanguageStrings.second, codeWithLanguage.second)
            apply()
        }
    }

    fun setPreviousLanguages() {
        currentButton.checkPair(currentButton.getStringsPrefPair())
        targetButton.checkPair(targetButton.getStringsPrefPair())
    }

    fun swapLanguages() {
        currentButton.language =
            targetButton.language.also { targetButton.language = currentButton.language }
        currentButton.languageCode = targetButton.languageCode.also {
            targetButton.languageCode = currentButton.languageCode
        }

        val buttonsList = listOf(currentButton, targetButton)
        buttonsList.forEach {
            it.language.notifyChange()
            saveLanguage(it.getStringsPair(), it.getCodeAndLanguagePair())
        }
    }

    fun translateText() {
        if (textToTranslate.isNullOrBlank()) {
            translateObserver.translatedText.set("")
            return
        }
        val translateDirection = "${currentButton.languageCode}-${targetButton.languageCode}"

        yandexService.translate(textToTranslate!!, translateDirection)
            .subscribeOn(Schedulers.io())
            .observeOn(mainThread())
            .subscribe(translateObserver)
    }
}