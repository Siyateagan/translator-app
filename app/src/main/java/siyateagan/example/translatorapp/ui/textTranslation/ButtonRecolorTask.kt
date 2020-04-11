package siyateagan.example.translatorapp.ui.textTranslation

import android.os.AsyncTask
import siyateagan.example.translatorapp.data.model.Dao
import siyateagan.example.translatorapp.data.model.FavoritesEntity
import siyateagan.example.translatorapp.util.ObservableVariable

class ButtonRecolorTask(
    private val textToTranslate: String,
    private val translatedText: String,
    private val translationDao: Dao,
    private val isColored: ObservableVariable<Boolean>
) :
    AsyncTask<Unit, Unit, FavoritesEntity?>() {
    override fun doInBackground(vararg params: Unit?): FavoritesEntity? {
        return translationDao.contains(textToTranslate, translatedText)
    }

    override fun onPostExecute(result: FavoritesEntity?) {
        super.onPostExecute(result)
        isColored.value = result != null
    }
}