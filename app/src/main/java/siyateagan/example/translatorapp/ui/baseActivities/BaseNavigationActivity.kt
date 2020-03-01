package siyateagan.example.translatorapp.ui.baseActivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.ui.favorites.FavoritesActivity
import siyateagan.example.translatorapp.ui.history.HistoryActivity
import siyateagan.example.translatorapp.ui.textTranslation.TextTranslationActivity

abstract class BaseNavigationActivity : BaseActivity() {
    abstract fun getClassName(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView = nav_view
        setItemsIntents(navView, this, getClassName())

        when (getLayoutResource()) {
            R.layout.activity_text_translation -> navView.menu.getItem(0).isChecked = true
            R.layout.activity_history -> navView.menu.getItem(1).isChecked = true
            R.layout.activity_favorites -> navView.menu.getItem(2).isChecked = true
        }
    }

    //TODO can this be refactored?
    private fun setItemsIntents(navView: BottomNavigationView, context: Context, activityName: String) {
        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_text_translation -> {
                    if (activityName != TextTranslationActivity::class.java.simpleName){
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
}