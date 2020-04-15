package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.speech.tts.TextToSpeech
import androidx.databinding.ObservableField
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.data.local.StringsHelper
import siyateagan.example.translatorapp.data.model.Dao
import siyateagan.example.translatorapp.data.model.FavoritesEntity
import siyateagan.example.translatorapp.data.observer.TranslateObserver
import siyateagan.example.translatorapp.data.remote.YandexService
import siyateagan.example.translatorapp.ui.base.DisposingViewModel
import siyateagan.example.translatorapp.util.ObservableVariable
import siyateagan.example.translatorapp.util.ParcelablePair
import java.util.*
import javax.inject.Inject

class TextTranslationVM @Inject constructor(
    private val translateObserver: TranslateObserver,
    private val applicationContext: Context,
    private val sharedPref: SharedPreferences,
    private val stringsHelper: StringsHelper,
    private val yandexService: YandexService,
    private val translationDao: Dao
) : DisposingViewModel() {

    var currentButton = LanguageButton.createCurrentButton(sharedPref, stringsHelper)
    var targetButton = LanguageButton.createTargetButton(sharedPref, stringsHelper)

    val textToTranslate: ObservableField<String?> = ObservableField("")
    val translatedText: ObservableField<String> = ObservableField("")

    /** If using only single tts object there will be big delay
     * if you listen to current and target text in turn.
     * So there are two tts object to prevent delay*/
    var currentTextToSpeech: TextToSpeech? = null
    var targetTextToSpeech: TextToSpeech? = null

    var isFavoritesColored = ObservableVariable(false)

    init {
        translateObserver.setTranslatedText(translatedText)
    }

    fun setNewLanguage(requestCode: Int, data: Intent?) {
        val codeWithLanguage =
            data?.getParcelableExtra<ParcelablePair<String, String>>(stringsHelper.codeWithLanguage())
                ?.toPair()
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
        swapTtsLanguages()
    }

    private fun swapLanguagesData() {
        currentButton.language =
            targetButton.language.also { targetButton.language = currentButton.language }
        currentButton.languageCode = targetButton.languageCode.also {
            targetButton.languageCode = currentButton.languageCode
        }
        textToTranslate.set(translatedText.get()
            .also { translatedText.set(textToTranslate.get()) })
    }

    private fun swapButtons() {
        val buttonsList = listOf(currentButton, targetButton)
        buttonsList.forEach {
            it.language.notifyChange()
            saveLanguage(it.getStringsPair(), it.getCodeAndLanguagePair())
        }
    }

    private fun swapTtsLanguages() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
        currentTextToSpeech = targetTextToSpeech.also { targetTextToSpeech = currentTextToSpeech }
    }

    fun translateText() {
        if (textToTranslate.get().isNullOrBlank()) {
            translatedText.set("")
            return
        }
        val translateDirection = "${currentButton.languageCode}-${targetButton.languageCode}"

        yandexService.translate(textToTranslate.get()!!, translateDirection)
            .subscribeOn(Schedulers.io())
            .observeOn(mainThread())
            .doAfterSuccess { checkInDb() }
            .subscribe(translateObserver)
    }

    private fun checkInDb() {
        val containsDisposable =
            translationDao.contains(textToTranslate.get()!!, translatedText.get()!!)
                .subscribeOn(Schedulers.computation())
                .observeOn(mainThread())
                .subscribe({ isFavoritesColored.value = true }, { isFavoritesColored.value = false })
        addDisposable(containsDisposable)
    }

    fun getResponseObservable() = translateObserver.isLoading.observable

    fun initTts() {
        currentTextToSpeech = TextToSpeech(
            applicationContext,
            TextToSpeech.OnInitListener { setTtsLanguage(stringsHelper.currentButton()) })

        targetTextToSpeech =
            TextToSpeech(
                applicationContext,
                TextToSpeech.OnInitListener { setTtsLanguage(stringsHelper.targetButton()) })
    }

    fun setTtsLanguage(languageButton: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
        if (currentButton.languageCode.isNullOrBlank() || targetButton.languageCode.isNullOrBlank()) return

        if (languageButton == stringsHelper.currentButton())
            currentTextToSpeech?.language = Locale.forLanguageTag(currentButton.languageCode!!)
        else targetTextToSpeech?.language = Locale.forLanguageTag(targetButton.languageCode!!)
    }

    fun addToFavorites() = Single.create<Boolean> {
        if (textToTranslate.get().isNullOrBlank() ||
            translatedText.get().isNullOrBlank()
        ) return@create

        val (favoritesEntity, dbEntity: FavoritesEntity?) = setEntities()
        updateDb(dbEntity, favoritesEntity, it)
    }

    private fun setEntities(): Pair<FavoritesEntity, FavoritesEntity?> {
        val favoritesEntity = FavoritesEntity(
            current = textToTranslate.get()!!,
            target = translatedText.get()!!
        )

        var dbEntity: FavoritesEntity? = null

        val dbDisposable = translationDao.contains(favoritesEntity.current, favoritesEntity.target)
            .subscribe({ result -> dbEntity = result }, { dbEntity = null })
        addDisposable(dbDisposable)

        return Pair(favoritesEntity, dbEntity)
    }

    private fun updateDb(
        dbEntity: FavoritesEntity?,
        favoritesEntity: FavoritesEntity,
        it: SingleEmitter<Boolean>
    ) {
        if (dbEntity == null) {
            translationDao.insert(favoritesEntity)
            it.onSuccess(true)
        } else {
            translationDao.delete(dbEntity)
            it.onSuccess(false)
        }
    }
}