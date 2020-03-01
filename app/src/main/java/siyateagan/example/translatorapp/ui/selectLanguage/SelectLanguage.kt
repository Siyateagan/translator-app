package siyateagan.example.translatorapp.ui.selectLanguage

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_select_language.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.ui.baseActivities.BaseActivity

class SelectLanguage : BaseActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewAdapter = RecyclerAdapter(
            listOf("1", "2", "3")
        )
        viewManager = LinearLayoutManager(this)

        recyclerView = recycler_languages.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun getLayoutResource() = R.layout.activity_select_language
}
