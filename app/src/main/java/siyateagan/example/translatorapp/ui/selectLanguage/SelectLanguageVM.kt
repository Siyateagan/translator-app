package siyateagan.example.translatorapp.ui.selectLanguage

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.data.observer.LanguagesObserver
import siyateagan.example.translatorapp.data.remote.YandexService
import siyateagan.example.translatorapp.ui.base.DisposingViewModel
import java.util.*
import javax.inject.Inject

class SelectLanguageVM @Inject constructor(
    private val yandexService: YandexService,
    private val languagesObserver: LanguagesObserver
) : DisposingViewModel() {
    fun getLanguages() {
        yandexService.getLangs(Locale.getDefault().language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(languagesObserver)
    }

    fun getRefreshObservable() = languagesObserver.isRefreshing.observable
}