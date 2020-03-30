package siyateagan.example.translatorapp.network

/**
 * Response content on getLanguages request of [siyateagan.example.translatorapp.network.YandexTranslateApi]
 */

data class AvailableLanguages(var langs: LinkedHashMap<String, String>?)
data class TranslatedText(
    val code: Int,
    val lang: String,
    val text: List<String>)