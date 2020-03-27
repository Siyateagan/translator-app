package siyateagan.example.translatorapp.ui.selectLanguage

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
    var isRefreshing: ObservableVariable<LanguagesResult> = ObservableVariable(LanguagesResult.Loading)

    @Inject
    lateinit var languagesObserver: LanguagesObserver

    fun getLanguages() {
        yandexService.getLangs(Locale.getDefault().language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { isRefreshing.value = LanguagesResult.Success }
            .doAfterTerminate {
                if(isRefreshing.value != LanguagesResult.Success){
                    isRefreshing.value = LanguagesResult.Error(languagesObserver.errorMessage)
                }
            }
            .subscribe(languagesObserver)
    }

    sealed class LanguagesResult {
        object Loading: LanguagesResult()
        object Success : LanguagesResult()
        data class Error(val errorMessage: String?) : LanguagesResult()
    }
}