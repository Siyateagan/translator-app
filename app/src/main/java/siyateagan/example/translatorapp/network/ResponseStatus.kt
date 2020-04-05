package siyateagan.example.translatorapp.network

sealed class ResponseStatus {
    object Init : ResponseStatus()
    object Loading : ResponseStatus()
    object Success : ResponseStatus()
    data class Error(val errorMessage: String?) : ResponseStatus()
}