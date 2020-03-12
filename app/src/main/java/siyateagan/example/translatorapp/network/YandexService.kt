package siyateagan.example.translatorapp.network

import io.reactivex.Observable
import javax.inject.Inject

class YandexService @Inject constructor(private val yandexTranslateApi: YandexTranslateApi) {
    private val API_KEY =
        "trnsl.1.1.20200215T122710Z.a02203f6d9b356f6.983ac741d035b35371b8c08559087b32b5f7587c"

    fun getLangs(uiLangCode: String?): Observable<AvailableLanguages?>? {
        return yandexTranslateApi.getLanguages(API_KEY, uiLangCode)
    }
}
