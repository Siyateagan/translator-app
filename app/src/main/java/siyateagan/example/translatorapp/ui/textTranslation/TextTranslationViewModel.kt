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

    var currentButton = LanguageButton(stringsHelper.getCurrentLanguage(), sharedPref, stringsHelper)
    var targetButton = LanguageButton(stringsHelper.getTargetLanguage(), sharedPref, stringsHelper)
    lateinit var buttonForWork: LanguageButton

    var translatedText = ObservableField("")
    var textToTranslate: String? = null

    fun setNewLanguage(requestCode: Int, data: Intent?) {
        val codeWithLanguage: Pair<String, String> =
            data?.getParcelableExtra<ParcelablePair<String, String>>("languageWithCode")?.pair
                ?: return

        buttonForWork = if (requestCode == 1) currentButton else targetButton
        buttonForWork.setLanguage(codeWithLanguage)

        val codeWithLanguageStrings: Pair<String, String> = buttonForWork.getStringsPair()
        setSharedPrefData(codeWithLanguageStrings, codeWithLanguage)
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

    /**
     * This method is called in TextTranslationActivity,
     * because its contents cannot be placed in the "init" block,
     * since applicationContext has not yet been initialized.
     */
    fun setPreviousLanguages() {
        currentButton.checkPair(currentButton.getStringsPair())
        targetButton.checkPair(targetButton.getStringsPair())
    }

    fun swapLanguages() {
        currentButton.language = targetButton.language.also { targetButton.language = currentButton.language }
        currentButton.languageCode = targetButton.languageCode.also { targetButton.languageCode = currentButton.languageCode }

        currentButton.language.notifyChange()
        targetButton.language.notifyChange()
    }

    fun translateText() {
        if (textToTranslate.isNullOrBlank()) {
            translatedText.set("")
            return
        }
        val translateDirection = "${currentButton.languageCode}-${targetButton.languageCode}"

        Log.e(TAG, "$textToTranslate $translateDirection")
        val disposable = yandexService.translate(textToTranslate!!, translateDirection)
            .subscribeOn(Schedulers.io())
            .observeOn(mainThread())
            .subscribe({ result -> translatedText.set(result.text[0]) },
                { error -> Log.e(TAG, "ERROR: ${error.message}") })
    }
}