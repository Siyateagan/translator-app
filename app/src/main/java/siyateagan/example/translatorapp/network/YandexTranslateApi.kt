package siyateagan.example.translatorapp.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for work with Retrofit2, describe requests to Yandex.Translate api with responses
 */
interface YandexTranslateApi {
    @GET("v1.5/tr.json/getLangs")
    fun getLanguages(
        @Query("key") apiKey: String?,
        @Query("ui") uiLangCode: String?
    ): Single<AvailableLanguages>
}