package siyateagan.example.translatorapp.ui.selectLanguage

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_select_language.*
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import kotlinx.android.synthetic.main.search_view_layout.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.network.YandexService
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import siyateagan.example.translatorapp.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject


class SelectLanguage : BaseActivity() {
    val TAG = this::class.java.simpleName

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var selectLanguageViewModel: SelectLanguageViewModel

    @Inject
    lateinit var yandexService: YandexService

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)
        setSupportActionBar(toolbar)
        setSearchView(search_view, search_divider)

        selectLanguageViewModel =
            ViewModelProvider(this, viewModelFactory).get(SelectLanguageViewModel::class.java)

        viewManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView = recycler_languages.apply {
            layoutManager = viewManager
            addItemDecoration(itemDecor)
        }

        val languagesDisposable = yandexService.getLangs(Locale.getDefault().language)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { availableLanguages ->
                    val data = availableLanguages?.langs?.values
                    recyclerView.adapter = data?.toList()?.let { LanguagesAdapter(it) }
                },
                { error -> Log.e(TAG, "{$error.message}") }
            )
    }
}
