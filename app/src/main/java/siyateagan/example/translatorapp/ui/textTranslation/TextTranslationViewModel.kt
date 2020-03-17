package siyateagan.example.translatorapp.ui.textTranslation

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TextTranslationViewModel @Inject constructor(): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}