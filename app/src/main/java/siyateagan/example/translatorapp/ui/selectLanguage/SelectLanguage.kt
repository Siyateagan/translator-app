package siyateagan.example.translatorapp.ui.selectLanguage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_select_language.*
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import kotlinx.android.synthetic.main.search_view_layout.*
import kotlinx.android.synthetic.main.search_view_layout.view.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.di.DaggerAppComponent
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import siyateagan.example.translatorapp.ui.base.BaseActivity
import javax.inject.Inject


class SelectLanguage : BaseActivity() {
    @Inject
    lateinit var languageViewModelFactory: LanguageViewModelFactory<SelectLanguageViewModel>
    lateinit var selectLanguageViewModel: SelectLanguageViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)
        setSupportActionBar(toolbar)
        removeSearchViewUnderline()

        //TODO check Multibindings
        DaggerAppComponent.builder().build().inject(this)
        selectLanguageViewModel =
            ViewModelProvider(this, languageViewModelFactory).get(
                SelectLanguageViewModel::class.java
            )

        search_layout.search_view.queryHint = selectLanguageViewModel.test

        viewAdapter =
            LanguagesAdapter(
                listOf("Эльфийский", "Russian", "Chinese")
            )
        viewManager = LinearLayoutManager(this)

        //TODO extract this?
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        recyclerView = recycler_languages.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(itemDecor)
        }
    }
}
