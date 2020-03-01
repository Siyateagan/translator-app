package siyateagan.example.translatorapp.ui.history

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.ui.baseActivities.BaseNavigationActivity


class HistoryActivity : BaseNavigationActivity() {
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    override fun getLayoutResource() = R.layout.activity_history
    override fun getClassName()= this::class.java.simpleName
}