package siyateagan.example.translatorapp.ui.history

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import kotlinx.android.synthetic.main.search_view_layout.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.ui.base.BaseNavigationActivity


class HistoryActivity : BaseNavigationActivity() {
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
        setSearchView(search_view, search_divider)
        setItemsIntents(nav_view, this, this::class.java.simpleName)

        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
    }
}