package siyateagan.example.translatorapp.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import siyateagan.example.translatorapp.data.remote.YandexTranslateApi
import javax.inject.Singleton

@Module
class NetworkModule {
    private val API_BASE_URL = "https://translate.yandex.net/api/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideYandexTranslateApi(retrofit: Retrofit): YandexTranslateApi {
        return retrofit.create(YandexTranslateApi::class.java)
    }
}