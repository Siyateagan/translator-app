package siyateagan.example.translatorapp.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is favorites Fragment"
    }
    val text: LiveData<String> = _text
}