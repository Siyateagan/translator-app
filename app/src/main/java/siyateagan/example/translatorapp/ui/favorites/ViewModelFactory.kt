package siyateagan.example.translatorapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject


class ViewModelFactory<T : ViewModel> @Inject constructor(private val favoritesViewModel: FavoritesViewModel) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return favoritesViewModel as T
    }
}
