package siyateagan.example.translatorapp.data.observer

import android.content.Context
import androidx.databinding.ObservableField
import io.reactivex.disposables.Disposable
import siyateagan.example.translatorapp.data.local.ResponseStatus
import siyateagan.example.translatorapp.data.model.TranslatedText
import siyateagan.example.translatorapp.data.observer.BaseSingleObserver
import siyateagan.example.translatorapp.util.ObservableVariable
import javax.inject.Inject

class TranslateObserver @Inject constructor(
    val applicationContext: Context,
    val translatedText: ObservableField<String>
) :
    BaseSingleObserver<TranslatedText>(applicationContext) {
    var isLoading: ObservableVariable<ResponseStatus> =
        ObservableVariable(ResponseStatus.Init)

    override fun onSubscribe(disposable: Disposable) {
        super.onSubscribe(disposable)
        isLoading.value = ResponseStatus.Loading
    }

    override fun onSuccess(data: TranslatedText) {
        super.onSuccess(data)
        translatedText.set(data.text[0])
        isLoading.value = ResponseStatus.Success
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        isLoading.value = ResponseStatus.Error(getErrorMessage(e))
    }
}