package siyateagan.example.translatorapp.ui.selectLanguage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_select_language.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.databinding.ActivitySelectLanguageBinding
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import siyateagan.example.translatorapp.ui.base.BaseActivity
import siyateagan.example.translatorapp.util.exhaustive
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

    lateinit var binding: ActivitySelectLanguageBinding

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
        selectLanguageViewModel.getLanguages()
        setSearchViewQuerySettings(binding.searchView, recyclerAdapter)

        val swipeRefreshListner = OnRefreshListener {
            val languagesDisposable = selectLanguageViewModel.isRefreshing.observable.subscribe {
                when (it) {
                    is SelectLanguageViewModel.LanguagesResult.Loading -> {
                        binding.refreshLayout.isRefreshing = true
                    }
                    is SelectLanguageViewModel.LanguagesResult.Success -> {
                        Log.e(TAG, "SUCCESS")
                        binding.refreshLayout.isRefreshing = false
                    }
                    is SelectLanguageViewModel.LanguagesResult.Error -> {
                        Log.e(TAG, "ERROR")
                        binding.refreshLayout.isRefreshing = false
                        binding.recyclerLanguages.visibility = View.GONE
                        binding.errorLayout.visibility = View.VISIBLE
                        binding.textErrorMessage.text = it.errorMessage
                    }
                }.exhaustive
            }
        }

        binding.refreshLayout.post {
            binding.refreshLayout.isRefreshing = true
            swipeRefreshListner.onRefresh()
        }
    }
}
