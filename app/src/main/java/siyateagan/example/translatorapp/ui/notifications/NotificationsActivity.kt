package siyateagan.example.translatorapp.ui.notifications

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_notifications.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.utils.setItemsListeners

class NotificationsActivity : AppCompatActivity() {
    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        val navView: BottomNavigationView = nav_view
        navView.menu.getItem(2).isChecked = true

        setItemsListeners(navView, this, this::class.java.simpleName)
    }
}