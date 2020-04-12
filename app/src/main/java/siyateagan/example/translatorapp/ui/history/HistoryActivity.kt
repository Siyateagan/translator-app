package siyateagan.example.translatorapp.ui.history

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.databinding.ActivityHistoryBinding
import siyateagan.example.translatorapp.ui.base.BaseNavigationActivity


class HistoryActivity : BaseNavigationActivity() {
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHistoryBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_history)

        setSupportActionBar(binding.layoutToolbar.toolbar)
        setItemsIntents(binding.layoutNavigation.navView, this, this::class.java.simpleName)

        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
    }
}