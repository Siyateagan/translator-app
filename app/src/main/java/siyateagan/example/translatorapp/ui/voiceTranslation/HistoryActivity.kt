package siyateagan.example.translatorapp.ui.voiceTranslation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_favorites.nav_view
import kotlinx.android.synthetic.main.toolbar.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.utils.setItemsListeners

class HistoryActivity : AppCompatActivity() {
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = nav_view
        navView.menu.getItem(1).isChecked = true

        setItemsListeners(navView, this, this::class.java.simpleName)
    }
}