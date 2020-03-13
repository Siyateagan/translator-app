package siyateagan.example.translatorapp.ui.selectLanguage

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.network.YandexService
import java.util.*
import javax.inject.Inject

class SelectLanguageViewModel @Inject constructor(
    private val yandexService: YandexService
) :
    ViewModel() {
    private val TAG = this::class.java.simpleName
    private lateinit var languages: MutableCollection<String>

    fun getLanguages(): Single<MutableCollection<String>> {
        return Single.create {
            yandexService.getLangs(Locale.getDefault().language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { availableLanguages ->
                        languages = availableLanguages?.langs?.values!!
                        it.onSuccess(languages)
                    },
                    { error -> Log.e(TAG, "{$error.message}") }
                )
        }
    }
}