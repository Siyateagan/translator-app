package siyateagan.example.translatorapp.data.local

sealed class ResponseStatus {
    object Init : ResponseStatus()
    object Loading : ResponseStatus()
    object Success : ResponseStatus()
    data class Error(val errorMessage: String?) : ResponseStatus()
}