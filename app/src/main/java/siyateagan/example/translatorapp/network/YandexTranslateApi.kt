package siyateagan.example.translatorapp.network

import io.reactivex.Observable
import retrofit2.http.*

/**
 * Interface for work with Retrofit2, describe requests to Yandex.Translate api with responses
 */
interface YandexTranslateApi {
    @GET("v1.5/tr.json/getLangs")
    fun getLanguages(
        @Query("key") apiKey: String?,
        @Query("ui") uiLangCode: String?
    ): Observable<AvailableLanguages?>?
}