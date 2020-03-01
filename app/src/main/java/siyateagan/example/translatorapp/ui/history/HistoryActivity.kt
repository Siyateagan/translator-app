package siyateagan.example.translatorapp.ui.history

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_favorites.nav_view
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.toolbar.toolbar
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

        val searchHistory: View = search_history.findViewById(androidx.appcompat.R.id.search_plate)
        searchHistory.setBackgroundColor(Color.WHITE)

        setItemsListeners(navView, this, this::class.java.simpleName)
    }
}