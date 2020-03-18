package siyateagan.example.translatorapp.ui.selectLanguage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_select_language.*
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import kotlinx.android.synthetic.main.search_view_layout.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import siyateagan.example.translatorapp.ui.base.BaseActivity
import javax.inject.Inject


class SelectLanguageActivity : BaseActivity() {
    val TAG = this::class.java.simpleName

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var selectLanguageViewModel: SelectLanguageViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var languagesDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)
        setSupportActionBar(toolbar)

        selectLanguageViewModel =
            ViewModelProvider(this, viewModelFactory).get(SelectLanguageViewModel::class.java)

        languagesDisposable = selectLanguageViewModel.getLanguages()
            .subscribe { availableLanguages ->
                recyclerView.adapter = LanguagesAdapter(availableLanguages.toMutableList(), this)
                setSearchView(search_view, search_divider, recyclerView.adapter as LanguagesAdapter)
            }

        viewManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView = recycler_languages.apply {
            layoutManager = viewManager
            addItemDecoration(itemDecor)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        languagesDisposable?.dispose()
    }
}
