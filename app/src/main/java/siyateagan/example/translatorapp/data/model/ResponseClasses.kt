package siyateagan.example.translatorapp.data.model

/**
 * Response content on getLanguages, translate requests of [siyateagan.example.translatorapp.data.remote.YandexTranslateApi]
 */

data class AvailableLanguages(var langs: LinkedHashMap<String, String>?)
data class TranslatedText(
    val code: Int,
    val lang: String,
    val text: List<String>)