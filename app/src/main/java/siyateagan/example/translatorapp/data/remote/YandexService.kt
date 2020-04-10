package siyateagan.example.translatorapp.data.remote

import io.reactivex.Single
import siyateagan.example.translatorapp.data.model.AvailableLanguages
import siyateagan.example.translatorapp.data.model.TranslatedText
import javax.inject.Inject

class YandexService @Inject constructor(private val yandexTranslateApi: YandexTranslateApi) {
    private val API_KEY =
        "trnsl.1.1.20200215T122710Z.a02203f6d9b356f6.983ac741d035b35371b8c08559087b32b5f7587c"

    fun getLangs(uiLangCode: String?): Single<AvailableLanguages> {
        return yandexTranslateApi.getLanguages(API_KEY, uiLangCode)
    }

    fun translate(textToTranslate: String, translateDirection: String): Single<TranslatedText> {
        return yandexTranslateApi.translate(API_KEY, textToTranslate, translateDirection, "plain")
    }
}
