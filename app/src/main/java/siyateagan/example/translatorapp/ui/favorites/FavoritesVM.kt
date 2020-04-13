package siyateagan.example.translatorapp.ui.favorites

import io.reactivex.Single
import siyateagan.example.translatorapp.data.model.Dao
import siyateagan.example.translatorapp.data.model.FavoritesEntity
import siyateagan.example.translatorapp.ui.base.DisposingViewModel
import javax.inject.Inject

class FavoritesVM @Inject constructor(private val translationDao: Dao) : DisposingViewModel() {

    fun getTranslationPairs() = Single.create<MutableList<FavoritesEntity>> {
        it.onSuccess(translationDao.getAll())
    }
}