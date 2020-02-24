package siyateagan.example.translatorapp.utils

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.ui.voiceTranslation.VoiceTranslationActivity
import siyateagan.example.translatorapp.ui.textTranslation.TextTranslationActivity
import siyateagan.example.translatorapp.ui.favorites.FavoritesActivity

/**
 * This function avoid code duplication when setting the BottomNavigationView
 * intents in each Activity
 */
fun setItemsListeners(navView: BottomNavigationView, context: Context, activityName: String) {
    navView.setOnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_text_translation -> {
                if (activityName != TextTranslationActivity::class.java.simpleName){
                    val intent = Intent(context, TextTranslationActivity::class.java)
                    context.startActivity(intent)
                }
            }

            R.id.navigation_voice_translation -> {
                if (activityName != VoiceTranslationActivity::class.java.simpleName) {
                    val intent = Intent(context, VoiceTranslationActivity::class.java)
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