package siyateagan.example.translatorapp.ui.selectLanguage

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.util.LanguagesObserver
import siyateagan.example.translatorapp.network.YandexService
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import java.util.*
import javax.inject.Inject

class SelectLanguageViewModel @Inject constructor(
    private val yandexService: YandexService
) :
    ViewModel() {
    private val TAG = this::class.java.simpleName

    @Inject
    lateinit var languagesObserver: LanguagesObserver

    fun getLanguages() {
        yandexService.getLangs(Locale.getDefault().language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(languagesObserver)
    }
}