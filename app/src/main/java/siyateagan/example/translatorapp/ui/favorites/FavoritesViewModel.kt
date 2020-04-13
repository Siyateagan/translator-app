package siyateagan.example.translatorapp.ui.favorites

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import siyateagan.example.translatorapp.data.model.Dao
import siyateagan.example.translatorapp.data.model.FavoritesEntity
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(private val translationDao: Dao) : ViewModel() {

    fun getTranslationPairs() = Single.create<MutableList<FavoritesEntity>> {
        it.onSuccess(translationDao.getAll())
    }
}