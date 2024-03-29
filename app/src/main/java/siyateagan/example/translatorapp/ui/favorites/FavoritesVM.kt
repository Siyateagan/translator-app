package siyateagan.example.translatorapp.ui.favorites

import io.reactivex.Single
import siyateagan.example.translatorapp.data.local.database.Dao
import siyateagan.example.translatorapp.data.local.database.FavoritesEntity
import siyateagan.example.translatorapp.ui.base.DisposingViewModel
import javax.inject.Inject

class FavoritesVM @Inject constructor(private val translationDao: Dao) : DisposingViewModel() {
    fun getFavorites() = Single.create<MutableList<FavoritesEntity>> {
        it.onSuccess(translationDao.getAll())
    }
}