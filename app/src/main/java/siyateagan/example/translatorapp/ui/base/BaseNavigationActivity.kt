package siyateagan.example.translatorapp.ui.base

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.ui.favorites.FavoritesActivity
import siyateagan.example.translatorapp.ui.history.HistoryActivity
import siyateagan.example.translatorapp.ui.textTranslation.TextTranslationActivity

abstract class BaseNavigationActivity : BaseActivity() {

    //TODO can this be refactored?
    protected fun setItemsIntents(
        navView: BottomNavigationView, context: Context, activityName: String
    ) {
        setNavItemChecked(activityName, navView)

        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_text_translation -> {
                    if (activityName != TextTranslationActivity::class.java.simpleName) {
                        val intent = Intent(context, TextTranslationActivity::class.java)
                        context.startActivity(intent)
                    }
                }

                R.id.navigation_history -> {
                    if (activityName != HistoryActivity::class.java.simpleName) {
                        val intent = Intent(context, HistoryActivity::class.java)
                        context.startActivity(intent)
                    }
                }

                R.id.navigation_favorites -> {
                    if (activityName != FavoritesActivity::class.java.simpleName) {
                        val intent = Intent(context, FavoritesActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun setNavItemChecked(
        activityName: String,
        navView: BottomNavigationView
    ) {
        when (activityName) {
            TextTranslationActivity::class.java.simpleName -> navView.menu.getItem(0)
                .isChecked = true
            HistoryActivity::class.java.simpleName -> navView.menu.getItem(1).isChecked = true
            FavoritesActivity::class.java.simpleName -> navView.menu.getItem(2).isChecked = true
        }
    }
}