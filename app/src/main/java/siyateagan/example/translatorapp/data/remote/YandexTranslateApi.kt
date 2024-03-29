package siyateagan.example.translatorapp.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import siyateagan.example.translatorapp.data.model.AvailableLanguages
import siyateagan.example.translatorapp.data.model.TranslatedText

/**
 * Interface for work with Retrofit2, describe requests to Yandex.Translate api with responses
 */
interface YandexTranslateApi {
    @GET("v1.5/tr.json/getLangs")
    fun getLanguages(
        @Query("key") apiKey: String?,
        @Query("ui") uiLangCode: String?
    ): Single<AvailableLanguages>

    @GET("v1.5/tr.json/translate")
    fun translate(
        @Query("key") apiKey: String?,
        @Query("text") textToTranslate: String?,
        @Query("lang") translateDirection: String?,
        @Query("format") format: String?
    ): Single<TranslatedText>
}