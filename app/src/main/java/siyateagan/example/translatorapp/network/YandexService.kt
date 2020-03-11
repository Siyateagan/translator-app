package siyateagan.example.translatorapp.network

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object YandexService {
    private const val API_BASE_URL = "https://translate.yandex.net/api/"
    private const val API_KEY =
        "trnsl.1.1.20200215T122710Z.a02203f6d9b356f6.983ac741d035b35371b8c08559087b32b5f7587c"

    fun getLangs(uiLangCode: String?): Observable<AvailableLanguages?>? {
        return yandexTranslateApi.getLanguages(API_KEY, uiLangCode)
    }

    private val yandexTranslateApi: YandexTranslateApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return@lazy retrofit.create(YandexTranslateApi::class.java)
    }
}
