package siyateagan.example.translatorapp.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_notifications.nav_view
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.utils.setItemsListeners

class DashboardActivity : AppCompatActivity() {
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        val navView: BottomNavigationView = nav_view
        navView.menu.getItem(1).isChecked = true

        setItemsListeners(navView, this, this::class.java.simpleName)
    }
}