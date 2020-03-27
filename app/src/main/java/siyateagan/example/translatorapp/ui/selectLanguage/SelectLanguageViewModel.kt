package siyateagan.example.translatorapp.ui.selectLanguage

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.network.ResponseStatus
import siyateagan.example.translatorapp.util.LanguagesObserver
import siyateagan.example.translatorapp.network.YandexService
import siyateagan.example.translatorapp.util.ObservableVariable
import java.util.*
import javax.inject.Inject

class SelectLanguageViewModel @Inject constructor(
    private val yandexService: YandexService
) :
    ViewModel() {
    private val TAG = this::class.java.simpleName
    var isRefreshing: ObservableVariable<ResponseStatus> =
        ObservableVariable(ResponseStatus.Loading)

    @Inject
    lateinit var languagesObserver: LanguagesObserver

    /**
     * doAfterTerminate is used only to handle errors since there is
     * no "doAfterError" method and implement an observer here will clog viewModel
     */
    fun getLanguages() {
        yandexService.getLangs(Locale.getDefault().language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { isRefreshing.value = ResponseStatus.Success }
            .doAfterTerminate {
                if (isRefreshing.value != ResponseStatus.Success) {
                    isRefreshing.value = ResponseStatus.Error(languagesObserver.errorMessage)
                }
            }
            .subscribe(languagesObserver)
    }
}