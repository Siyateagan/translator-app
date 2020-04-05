package siyateagan.example.translatorapp.network.observer

import android.content.Context
import androidx.databinding.ObservableField
import siyateagan.example.translatorapp.network.TranslatedText
import javax.inject.Inject

class TranslateObserver @Inject constructor(
    val applicationContext: Context,
    val translatedText: ObservableField<String>
) :
    BaseSingleObserver<TranslatedText>(applicationContext) {

    override fun onSuccess(data: TranslatedText) {
        super.onSuccess(data)
        translatedText.set(data.text[0])
    }
}