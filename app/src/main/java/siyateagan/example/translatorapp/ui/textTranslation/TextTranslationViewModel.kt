package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.speech.tts.TextToSpeech
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.network.YandexService
import siyateagan.example.translatorapp.network.observer.TranslateObserver
import siyateagan.example.translatorapp.util.ParcelablePair
import siyateagan.example.translatorapp.util.StringsHelper
import java.util.*
import javax.inject.Inject

class TextTranslationViewModel @Inject constructor(
    private val context: Context,
    private val sharedPref: SharedPreferences,
    stringsHelper: StringsHelper,
    private val yandexService: YandexService
) : ViewModel() {
    private val TAG = TextTranslationViewModel::class.java.simpleName

    var currentButton = LanguageButton.createCurrentButton(sharedPref, stringsHelper)
    var targetButton = LanguageButton.createTargetButton(sharedPref, stringsHelper)

    var textToTranslate: ObservableField<String?> = ObservableField("")

    @Inject
    lateinit var translateObserver: TranslateObserver

    @Inject
    lateinit var stringsHelper: StringsHelper

    /** If using only single tts object there will be big delay
     * if you listen to current and target text in turn.
     * So there are two tts object to prevent delay*/
    var currentTextToSpeech: TextToSpeech? = null
    var targetTextToSpeech: TextToSpeech? = null

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
        swapLanguagesData()
        swapButtons()
    }

    private fun swapLanguagesData() {
        currentButton.language =
            targetButton.language.also { targetButton.language = currentButton.language }
        currentButton.languageCode = targetButton.languageCode.also {
            targetButton.languageCode = currentButton.languageCode
        }
        textToTranslate.set(translateObserver.translatedText.get()
            .also { translateObserver.translatedText.set(textToTranslate.get()) })
    }

    private fun swapButtons() {
        val buttonsList = listOf(currentButton, targetButton)
        buttonsList.forEach {
            it.language.notifyChange()
            saveLanguage(it.getStringsPair(), it.getCodeAndLanguagePair())
        }
    }

    fun translateText() {
        if (textToTranslate.get().isNullOrBlank()) {
            translateObserver.translatedText.set("")
            return
        }
        val translateDirection = "${currentButton.languageCode}-${targetButton.languageCode}"

        yandexService.translate(textToTranslate.get()!!, translateDirection)
            .subscribeOn(Schedulers.io())
            .observeOn(mainThread())
            .subscribe(translateObserver)
    }

    fun getResponseObservable() = translateObserver.isLoading.observable

    fun initTts() {
        currentTextToSpeech = TextToSpeech(
            context,
            TextToSpeech.OnInitListener { setTtsLanguage(stringsHelper.currentButton()) })

        targetTextToSpeech =
            TextToSpeech(
                context,
                TextToSpeech.OnInitListener { setTtsLanguage(stringsHelper.targetButton()) })
    }

    fun swapTtsLanguages() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
        currentTextToSpeech?.language = targetTextToSpeech?.voice?.locale.also {
            targetTextToSpeech?.language = currentTextToSpeech?.voice?.locale
        }
    }

    fun setTtsLanguage(languageButton: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return

        if (languageButton == stringsHelper.currentButton())
            currentTextToSpeech?.language = Locale.forLanguageTag(currentButton.languageCode!!)
        else
            targetTextToSpeech?.language = Locale.forLanguageTag(targetButton.languageCode!!)
    }
}