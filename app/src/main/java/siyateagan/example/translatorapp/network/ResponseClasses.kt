package siyateagan.example.translatorapp.network

/**
 * Response content on getLanguages request of [siyateagan.example.translatorapp.network.YandexTranslateApi]
 */

data class AvailableLanguages(var langs: HashMap<String, String>?)