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

    fun getLanguages(): Single<MutableList<String>> {
        return Single.create {
            yandexService.getLangs(Locale.getDefault().language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { availableLanguages ->
                        val sortedLanguages: MutableList<String>? =
                            availableLanguages.langs?.values?.sorted() as MutableList<String>?
                        sortedLanguages?.let { langs -> it.onSuccess(langs) }
                    },
                    { error -> Log.e(TAG, "{$error.message}") }
                )
        }
    }
}