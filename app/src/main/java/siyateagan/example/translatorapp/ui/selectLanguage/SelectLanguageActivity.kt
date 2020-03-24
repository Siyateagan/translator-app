package siyateagan.example.translatorapp.ui.selectLanguage

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_select_language.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.databinding.ActivitySelectLanguageBinding
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import siyateagan.example.translatorapp.ui.base.BaseActivity
import javax.inject.Inject

class SelectLanguageActivity @Inject constructor() : BaseActivity() {
    val TAG = this::class.java.simpleName

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var recyclerAdapter: LanguagesAdapter

    private lateinit var selectLanguageViewModel: SelectLanguageViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivitySelectLanguageBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_select_language)

        setSupportActionBar(binding.toolbar)
        setSearchView(binding.searchView, binding.searchDivider)

        selectLanguageViewModel =
            ViewModelProvider(this, viewModelFactory).get(SelectLanguageViewModel::class.java)

        viewManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView = recycler_languages.apply {
            layoutManager = viewManager
            addItemDecoration(itemDecor)
        }

        recyclerView.adapter = recyclerAdapter
        selectLanguageViewModel.getLanguages()
        setSearchViewQuerySettings(binding.searchView, recyclerAdapter)
    }
}
