package siyateagan.example.translatorapp.utils

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.ui.dashboard.DashboardActivity
import siyateagan.example.translatorapp.ui.home.HomeActivity
import siyateagan.example.translatorapp.ui.notifications.NotificationsActivity

/**
 * This function avoid code duplication when setting the BottomNavigationView
 * intents in each Activity
 */
fun setItemsListeners(navView: BottomNavigationView, context: Context, activityName: String) {
    navView.setOnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_home -> {
                if (activityName != HomeActivity::class.java.simpleName){
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                }
            }

            R.id.navigation_dashboard -> {
                if (activityName != DashboardActivity::class.java.simpleName) {
                    val intent = Intent(context, DashboardActivity::class.java)
                    context.startActivity(intent)
                }
            }

            R.id.navigation_notifications -> {
                if (activityName != NotificationsActivity::class.java.simpleName) {
                    val intent = Intent(context, NotificationsActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
        return@setOnNavigationItemSelectedListener false
    }
}