package siyateagan.example.translatorapp.ui.selectLanguage

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.toolbar.toolbar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_select_language.*
import kotlinx.android.synthetic.main.search_view_layout.*
import siyateagan.example.translatorapp.R

class SelectLanguage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)
        setSupportActionBar(toolbar)

        val searchHistory: View = search_view.findViewById(androidx.appcompat.R.id.search_plate)
        searchHistory.setBackgroundColor(Color.WHITE)

        viewAdapter = RecyclerAdapter(
            listOf("1", "2", "3")
        )
        viewManager = LinearLayoutManager(this)

        recyclerView = recycler_languages.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
