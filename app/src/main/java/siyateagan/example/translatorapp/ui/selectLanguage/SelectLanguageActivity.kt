package siyateagan.example.translatorapp.ui.selectLanguage

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
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

    private lateinit var binding: ActivitySelectLanguageBinding
    private val disposables = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_language)

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
        setSearchViewQuerySettings(binding.searchView, recyclerAdapter)

        if (recyclerAdapter.isDataAlreadyLoaded()) {
            val languagesDisposable = selectLanguageViewModel.isRefreshing.observable.subscribe {
                displayResult(it)
            }
            disposables.add(languagesDisposable)

            val swipeRefreshListener = OnRefreshListener {
                selectLanguageViewModel.getLanguages()
            }

            binding.refreshLayout.setOnRefreshListener(swipeRefreshListener)

            binding.refreshLayout.post {
                binding.refreshLayout.isRefreshing = true
                swipeRefreshListener.onRefresh()
            }
        } else binding.refreshLayout.isEnabled = false
    }

    private fun displayResult(result: SelectLanguageViewModel.LanguagesResult?) {
        when (result) {
            is SelectLanguageViewModel.LanguagesResult.Loading -> {
                if (binding.refreshLayout.isEnabled) {
                    binding.refreshLayout.isRefreshing = true
                }
            }
            is SelectLanguageViewModel.LanguagesResult.Success -> {
                binding.refreshLayout.isRefreshing = false
                binding.refreshLayout.isEnabled = false
                binding.recyclerLanguages.visibility = View.VISIBLE
                binding.errorLayout.visibility = View.GONE
            }
            is SelectLanguageViewModel.LanguagesResult.Error -> {
                binding.refreshLayout.isRefreshing = false
                binding.recyclerLanguages.visibility = View.GONE
                binding.errorLayout.visibility = View.VISIBLE
                binding.textErrorMessage.text = result.errorMessage
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
